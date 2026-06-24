package com.sts.sinorita.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.BalTriggerParam;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.dto.response.balanceAdjustment.BalTriggerRuleKeyMap;
import com.sts.sinorita.mapper.pricePlan.trigger.BalTriggerMapper;
import com.sts.sinorita.projection.balanceAdjustment.SelectThresholdProjection;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.repository.BalTriggerRepository;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class BillingCache {

  @Autowired
  private BalTriggerRepository balTriggerRepository;

  @Autowired
  private BalTriggerMapper balTriggerMapper;

  @Autowired
  private AcctResRepository acctResRepository;

  public List<BalTriggerParam> getBalTriggerRuleParam(Long pricePlanId, Long acctResId, String triggerType) {
    AssertUtil.isNotNull(pricePlanId, "pricePlanId");
    AssertUtil.isNotNull(acctResId, "acctResId");
    AssertUtil.isNotEmpty(triggerType, "triggerType");
    // DynamicDict balTriggerRuleKeyMap =
    // (DynamicDict)CacheHelper.getMapValue("BILLING", "BAL_TRIGGER_RULE", new
    // Object[] { pricePlanId });
    List<BalTriggerParam> balTriggerList = null;
    // if (balTriggerRuleKeyMap == null) {
    balTriggerList = balTriggerRepository.selectBalTrigger(pricePlanId).stream()
        .map(balTriggerMapper::toBalTriggerParams).toList();
    BalTriggerRuleKeyMap balTriggerRuleKeyMap = new BalTriggerRuleKeyMap();
    getTriggerParam(balTriggerList, balTriggerRuleKeyMap, pricePlanId);
    // CacheHelper.putMapValue("BILLING", "BAL_TRIGGER_RULE", (Serializable)
    // balTriggerRuleKeyMap,
    // new Object[] { pricePlanId });
    // }
    balTriggerList = getEffectiveTriggerParam(balTriggerRuleKeyMap, acctResId, triggerType);
    balTriggerList = filterThreshold(balTriggerList);
    return balTriggerList;
  }

  private List<BalTriggerParam> filterThreshold(List<BalTriggerParam> balSubsEventList) {
    if (CommonUtil.isEmpty(balSubsEventList))
      return null;
    List<BalTriggerParam> returnParamList = new ArrayList<>();
    for (BalTriggerParam balTriggerParam : balSubsEventList) {
      if (balTriggerParam == null || balTriggerParam.getPricePlanId() == null ||
          StringUtil.isEmpty(balTriggerParam.getAcctResIdList())
          || StringUtil.isEmpty(balTriggerParam.getTriggerType()))
        continue;
      if (null == balTriggerParam.getThreshold() && null == balTriggerParam.getRatio())
        continue;
      returnParamList.add(balTriggerParam);
    }
    return returnParamList;
  }

  private List<BalTriggerParam> getEffectiveTriggerParam(BalTriggerRuleKeyMap balTriggerRuleKeyMap,
      Long acctResId,
      String triggerType) {
    Map<String, List<Long[]>> balTriggerMap = (Map<String, List<Long[]>>) balTriggerRuleKeyMap.getTrigger();
    if (balTriggerMap.isEmpty())
      return null;
    Map<String, List<BalTriggerParam>> balTriggerParamMap = (Map<String, List<BalTriggerParam>>) balTriggerRuleKeyMap
        .getThresholdMap();
    LocalDateTime dateNow = DateUtil.GetDBDateTime();
    List<BalTriggerParam> returnBalTriggerParam = new ArrayList<>();
    List<Long[]> triggerList = balTriggerMap.get(acctResId.toString() + "|" + triggerType);
    if (CommonUtil.isEmpty(triggerList))
      return returnBalTriggerParam;
    for (Long[] trigger : triggerList) {
      long dateNowMillis = dateNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
      if (trigger[2].longValue() > dateNowMillis)
        continue;
      if (trigger[1].longValue() > dateNowMillis)
        continue;
      returnBalTriggerParam.addAll(balTriggerParamMap.get(trigger[0].toString()));
    }
    return returnBalTriggerParam;
  }

  private void getTriggerParam(List<BalTriggerParam> balTriggerList, BalTriggerRuleKeyMap balTriggerRuleKeyMap,
      Long pricePlanId) {
    if (CommonUtil.isEmpty(balTriggerList)) {
      Map<String, List<Long[]>> balTriggerMap = new HashMap<>();
      balTriggerRuleKeyMap.setTrigger(balTriggerMap);
      return;
    }
    StringBuilder triggerIds = new StringBuilder();
    Map<String, List<Long[]>> triggerMap = new HashMap<>();
    for (BalTriggerParam trigger : balTriggerList) {
      triggerIds.append(',').append(trigger.getTriggerId().toString());
      if (StringUtil.isEmpty(trigger.getAcctResIdList()))
        continue;
      String[] acctResIds = trigger.getAcctResIdList().split(",");
      for (int j = 0; j < acctResIds.length; j++) {
        AcctResDto acctResDto = acctResRepository.selectAcctRes(Long.valueOf(acctResIds[j])).get();
        if (acctResDto != null) {
          String key = acctResDto.getAcctResId().toString() + "|" + trigger.getTriggerType();
          Long[] triggerArray = { trigger.getTriggerId(),
              trigger.getTriggerEffDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
              trigger.getPriceEffDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() };
          if (triggerMap.containsKey(key)) {
            ((List<Long[]>) triggerMap.get(key)).add(triggerArray);
          } else {
            triggerMap.put(key, (List) new ArrayList<>());
            ((List<Long[]>) triggerMap.get(key)).add(triggerArray);
          }
        }
      }
    }
    String allTriggerIds = triggerIds.substring(1);
    Map<String, List<BalTriggerParam>> balTriggerParamMap = selectThreshold(allTriggerIds);
    balTriggerRuleKeyMap.setTrigger(triggerMap);
    balTriggerRuleKeyMap.setThresholdMap(balTriggerParamMap);
  }

  public Map<String, List<BalTriggerParam>> selectThreshold(String triggerIds) {

    List<SelectThresholdProjection> rows = balTriggerRepository.selectThreshold(triggerIds);

    Map<String, List<BalTriggerParam>> resultMap = new HashMap<>();
    Map<String, BalTriggerParam> uniqueMap = new HashMap<>();

    for (SelectThresholdProjection row : rows) {

      String uniqueKey = row.getTriggerId() + "-" + row.getBalThresholdId();

      BalTriggerParam dto = uniqueMap.get(uniqueKey);

      if (dto == null) {

        dto = new BalTriggerParam();
        dto.setThreshold(row.getThresholdValue());
        dto.setRatio(row.getRatio());
        dto.setBalThresholdId(row.getBalThresholdId());
        dto.setTriggerType(row.getTriggerType());
        dto.setTriggerId(row.getTriggerId());
        dto.setAcctResId(row.getAcctResId());
        dto.setPricePlanId(row.getPricePlanId());
        dto.setAcctResIdList(row.getAcctResIdList());
        dto.setReAttr(row.getReAttr());

        dto.setSubsEventList(new ArrayList<>());
        dto.setAdviceList(new ArrayList<>());

        uniqueMap.put(uniqueKey, dto);

        resultMap
            .computeIfAbsent(
                row.getTriggerId().toString(),
                k -> new ArrayList<>())
            .add(dto);
      }

      if (row.getSubsEventId() != null || row.getExtAttr() != null) {
        dto.getSubsEventList().add(
            dto.new SubsEvent(
                row.getSubsEventId(),
                row.getExtAttr()));
      }

      if (row.getAdviceType() != null) {
        dto.getAdviceList().add(
            dto.new Advice(
                row.getAdviceType(),
                ""));
      }
    }

    return resultMap;
  }

}
