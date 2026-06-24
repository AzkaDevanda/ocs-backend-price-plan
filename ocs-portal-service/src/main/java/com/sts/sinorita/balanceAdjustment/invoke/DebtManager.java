package com.sts.sinorita.balanceAdjustment.invoke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.balanceAdjustment.AcctService;
import com.sts.sinorita.balanceAdjustment.BalManager;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.AsynCallDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalCreditOrderDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalanceChangeTriggerDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ExtAttrBalanceChangeTrigger;
import com.sts.sinorita.dto.request.balanceAdjustment.PpsDueStateSubsEventDto;
import com.sts.sinorita.dto.request.balanceAdjustment.StateChangeDict;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.BalanceChangeTriggerDict;
import com.sts.sinorita.dto.request.priceplan.treshold.SubsEventDto;
import com.sts.sinorita.helper.AccountHelper;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.mapper.BalCreditOrderMapper;
import com.sts.sinorita.mapper.acct.AcctMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AsynCallMapper;
import com.sts.sinorita.mapper.balanceAdjustment.BalMapper;
import com.sts.sinorita.mapper.balanceAdjustment.PpsDueStateSubsEventMapper;
import com.sts.sinorita.mapper.balanceAdjustment.SubsEventMapper;
import com.sts.sinorita.mapper.balanceAdjustment.SubsMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctRepository;
import com.sts.sinorita.repository.AsynCallRepository;
import com.sts.sinorita.repository.BalCreditOrderRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.PpsDueStateSubsEventRepository;
import com.sts.sinorita.repository.SubsEventRepository;
import com.sts.sinorita.repository.SubsRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;
import com.sts.sinorita.util.ValidateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class DebtManager {

  private final BalMapper balMapper;

  private final BalManager balManager;

  private final SystemParamRepository systemParamRepository;

  private final BillingSeqHelper billingSeqHelper;

  private final AsynCallRepository asynCallRepository;

  private final AsynCallMapper asynCallMapper;

  private final BalCreditOrderRepository balCreditOrderRepository;

  private final BalCreditOrderMapper balCreditOrderMapper;

  private final AccountHelper accountHelper;

  private final SubsEventRepository subsEventRepository;

  private final SubsEventMapper subsEventMapper;

  private final PpsDueStateSubsEventRepository ppsDueStateSubsEventRepository;

  private final PpsDueStateSubsEventMapper ppsDueStateSubsEventMapper;

  private final AcctService acctService;

  private final AcctMapper acctMapper;

  private final SubsRepository subsRepository;

  private final SubsMapper subsMapper;

  private final ConfigItemRepository configItemRepository;

  public void balanceChangeTrigger(BalanceChangeTriggerDict triggerDto) {
    log.debug("begin balanceChangeTrigger");
    log.debug("BalanceChangeTriggerDto :" + triggerDto.getSpId());
    ValidateUtil.notNull(triggerDto, "BalanceChangeTriggerDto");
    ValidateUtil.notNull(triggerDto.getNewBasicBal(), "triggerDto.newBasicBalDto is null");
    ValidateUtil.notNull(triggerDto.getAcctId(), "triggerDto.acctId is null.");
    // if (triggerDto.getExtAttr() == null)
    // triggerDto.getExtAttr() = new DynamicDict();
    ExtAttrBalanceChangeTrigger extAttrBo = triggerDto.getExtAttr();
    String isValidateAcctBookType = extAttrBo.getIsValidateAcctBookType();
    if (!"N".equals(isValidateAcctBookType)) {
      ValidateUtil.notNull(triggerDto.getAcctBookType(), "triggerDto.acctBookType is null.");
      // String forbiddenTriggerAcctBookType = ConfigItemCache.instance()
      // .getString("ACCT.COLLECTION.NOT_TRIGGER_ACCT_BOOK_TYPE", "");
      String forbiddenTriggerAcctBookType = configItemRepository
          .findConfigItem("ACCT", "COLLECTION", "NOT_TRIGGER_ACCT_BOOK_TYPE")
          .map(ConfigItemParamProjection::getParamValue).orElse("");
      String[] forbiddenTriggerAcctBookTypeList = forbiddenTriggerAcctBookType.split(",");
      for (int i = 0; i < forbiddenTriggerAcctBookTypeList.length; i++) {
        if (forbiddenTriggerAcctBookTypeList[i].equalsIgnoreCase(triggerDto.getAcctBookType())) {
          log.debug("this account book type is forbidden [{}] ", new Object[] { triggerDto.getAcctBookType() });
          return;
        }
      }
    }
    SubsDto[] subsDtoList = null;
    // SubsInfoMgr subsInfoMgr = new SubsInfoMgr();
    Long acctId = triggerDto.getAcctId();
    subsDtoList = subsRepository.selectSubsByAcctId(acctId).stream()
        .map(subsMapper::toSubsDtoFromSelectAllSubsByAcctId)
        .toArray(SubsDto[]::new);
    if (CommonUtil.isEmpty((Object[]) subsDtoList)) {
      log.debug("DON'T HAVE SUBSCRIBERS TO DEAL WITH");
      return;
    }
    LocalDateTime dateTimeNow = DateUtil.GetDBDateTime();
    subsDtoList = triggerDto.getAllSubsDtoList();
    dateTimeNow = triggerDto.getDateTimeNow();
    BalanceChangeTriggerDict triggerDict = new BalanceChangeTriggerDict();
    if (triggerDto.getExtAttr() != null) {
      Long subsId = triggerDto.getExtAttr().getSubsId();
      triggerDict.setSubsId(subsId);
      triggerDict.setIsLoanFreeEff(triggerDto.getExtAttr().getIsLoanFreeEff());
    }
    triggerDict.setSpId(triggerDto.getSpId());
    triggerDict.setCharge(extAttrBo.getCharge());
    prodStateChangeNew(triggerDict);
    if (triggerDto.getAddBalList() != null) {
      triggerDto.setAddBalList(triggerDto.getAddBalList());
      triggerDto.setCurBalList(triggerDto.getCurBalList());
      // if (log.isDebugEnabled())
      // log.debug("return triggerDict = [{}]", new Object[] { triggerDict.asXML(null)
      // });
    }
    if (triggerDto.getExtAttr() != null) {
      triggerDto.getExtAttr().setSubsEventId(triggerDict.getSubsEventId());
      // triggerDto.getExtAttr().set("ONCE_FEE", triggerDict.getLong("ONCE_FEE"));
      // triggerDto.getExtAttr().setIsNeedLifeCycleInfo(triggerDict.getIsNeedLifeCycleInfo());
    }
  }

  public void prodStateChangeNew(BalanceChangeTriggerDict dict) {
    SubsDto[] subsList = dict.getAllSubsDtoList();
    List<Long> activeSubsIdList = dict.getActiveSubsIdList();
    if (subsList == null && activeSubsIdList == null) {
      log.debug("NO SUBS TO DEAL WITH");
      return;
    }
    LocalDateTime dateNow = dict.getDateTimeNow();
    if (dateNow == null)
      dateNow = DateUtil.GetDBDateTime();
    String expDateChange = getExpireDateChange(dict, dateNow);
    String chargeChangeState = getChargeChangeStateChange(dict);
    Map<Long, List<Long>> subsEventToSubsListMap = new HashMap<>();
    String contactChannelId = dict.getContactChannelId();
    if (subsList != null) {
      Long acctId = dict.getAcctId();
      // AcctInfoMgr acctInfoMgr = new AcctInfoMgr();
      String postpaid = null;
      if (dict.getAcct() == null) {
        AcctDto acctDto = acctService.selectAcctDtoByAcctId(acctId, false)
            .map(acctMapper::toAcctDtoFromSelectAcctDtoByAcctId).orElse(null);
        if (acctDto == null) {
          log.debug("The specified account does not exist.ACCT_ID=[{}]", new Object[] { acctId });
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00393"));
          // throw ExceptionHandler.publish("S-ACT-00393", 0);
        }
        postpaid = acctDto.getPostpaid();
      } else {
        postpaid = dict.getAcct().getPostpaid();
      }
      // Long activeSubsCharge =
      // ConfigItemCache.instance().getLong("ACCT.ACCOUNT_PUBLIC.ACTIVE_SUBS_CHARGE");
      Long activeSubsCharge = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC", "ACTIVE_SUBS_CHARGE")
          .map(ConfigItemParamProjection::getParamValue)
          .map(String::trim)
          .filter(value -> !value.isEmpty())
          .map(Long::valueOf)
          .orElse(null);
      for (SubsDto subsDto : subsList) {
        String dueStateChange = getDueStateChange(dict, subsDto, postpaid, dateNow);
        String prodState = subsDto.getProd().getProdState();
        String blockReason = subsDto.getProd().getBlockReason();
        String stateChange = "'" + dueStateChange + "'";
        if (StringUtil.isNotEmpty(chargeChangeState))
          stateChange = stateChange + ",'" + chargeChangeState + "'";
        if ("G".equals(prodState) && dict.getCharge() != null && activeSubsCharge != null)
          if (dict.getCharge().longValue() + activeSubsCharge.longValue() > 0L)
            continue;
        Long[] subsEventIdArr = qrySubsEventIdForBalanceChangeTrigger(prodState, stateChange, expDateChange,
            contactChannelId, blockReason);
        for (int i = 0; i < subsEventIdArr.length; i++) {
          if (subsEventToSubsListMap.get(subsEventIdArr[i]) != null) {
            ((List<Long>) subsEventToSubsListMap.get(subsEventIdArr[i])).add(subsDto.getSubsId());
          } else {
            List<Long> subsIdList = new ArrayList<>();
            subsIdList.add(subsDto.getSubsId());
            subsEventToSubsListMap.put(subsEventIdArr[i], subsIdList);
          }
        }
      }
      if (!subsEventToSubsListMap.isEmpty())
        modProdState(subsEventToSubsListMap, dict);
    }
  }

  private Long[] qrySubsEventIdForBalanceChangeTrigger(String prodState, String dueStateChange, String expireDateChange,
      String contactChannelId, String blockReason) {
    List<Long> subsEventIdList = new ArrayList<>();
    Long[] subsEventIdArray = new Long[0];
    // IPpsDueStateSubsEventDAO ppsDueStateSubsEventDAO = (IPpsDueStateSubsEventDAO)
    // DAOFactory.createModuleDAO(
    // "PpsDueStateSubsEvent", "billing.coreapi",
    // JdbcUtil4BL.getDbBackService());
    List<PpsDueStateSubsEventDto> ppsDueStateSubsEventDtoList = ppsDueStateSubsEventRepository
        .selectSubsEventIdForBalanceChangeTrigger(prodState, dueStateChange, expireDateChange).stream()
        .map(ppsDueStateSubsEventMapper::tPpsDueStateSubsEventDtoFromSelectSubsEventIdForBalanceChangeTrigger).toList();
    for (PpsDueStateSubsEventDto ppsDueStateSubsEventDto : ppsDueStateSubsEventDtoList) {
      boolean isNotSuit = AccountHelper.isInCommaText(ppsDueStateSubsEventDto.getUnsuitChannelList(), contactChannelId);
      if (isNotSuit)
        continue;
      Long subsEventId = ppsDueStateSubsEventDto.getSubsEvent();
      if (checkPreBlockReason(ppsDueStateSubsEventDto.getBlockReason(), blockReason)
          || checkAfterBlockReason(subsEventId, blockReason))
        continue;
      subsEventIdList.add(subsEventId);
    }
    if (!subsEventIdList.isEmpty())
      subsEventIdArray = subsEventIdList.<Long>toArray(new Long[subsEventIdList.size()]);
    return subsEventIdArray;
  }

  private boolean checkPreBlockReason(String neededBlockReason, String curBlockReason) {
    if (StringUtil.isEmpty(neededBlockReason) || Pattern.matches("^9+$", neededBlockReason))
      return false;
    if (StringUtil.isEmpty(curBlockReason)) {
      log.debug("Current block reason should be [{}], but found null.", new Object[] { neededBlockReason });
      return true;
    }
    boolean ret = false;
    char noNeedCheck = '9';
    for (int i = 0; i < neededBlockReason.length(); i++) {
      char tmp = neededBlockReason.charAt(i);
      if (tmp != noNeedCheck)
        if (tmp == curBlockReason.charAt(i)) {
          ret = true;
          break;
        }
    }
    log.debug("Campare current[{}] to needed[{}], result is [{}]",
        new Object[] { curBlockReason, neededBlockReason, Boolean.valueOf(ret) });
    return !ret;
  }

  private boolean checkAfterBlockReason(Long subsEventId, String blockReason) {
    if (StringUtil.isEmpty(blockReason))
      return false;
    SubsEventDto subsEventDto = subsEventRepository.selectSubsEvent(subsEventId)
        .map(subsEventMapper::toSubsEventDtoFromSelectSubEvent).orElse(null);
    String stateSet = subsEventDto.getStateSet();
    if (StringUtil.isEmpty(stateSet)) {
      log.debug("SUBS_EVENT.STATE_SET is empty, not need to check.");
      return false;
    }
    boolean ret = Pattern.matches(stateSet.replaceAll("9", "\\\\d"), blockReason);
    log.debug("blockReason[{}] compare to state_set[{}], Is result incorrect? [{}]",
        new Object[] { blockReason, stateSet, Boolean.valueOf(ret) });
    return ret;
  }

  public String getExpireDateChange(BalanceChangeTriggerDict dict, LocalDateTime dateNow) {
    // BalManager balManager = new BalManager();
    String expireDateChange = "X";
    BalDto[] curBalDtoList = dict.getCurBalList();
    BalDto[] oldBalDtoList = dict.getOldBalList();
    BalDto curDefaultBal = balManager.getBasicBal(curBalDtoList);
    BalDto oldDefaultBal = balManager.getBasicBal(oldBalDtoList);
    String curExpState = "NE";
    String oldExpState = "NE";
    if (curDefaultBal != null && curDefaultBal.getExpDate() != null) {
      int compare = DateUtil.compare(curDefaultBal.getExpDate(), dateNow);
      if (compare == 0)
        curExpState = "E";
    }
    if (oldDefaultBal != null && oldDefaultBal.getExpDate() != null) {
      int compare = DateUtil.compare(oldDefaultBal.getExpDate(), dateNow);
      if (compare == 0)
        oldExpState = "E";
    }
    if (!curExpState.equals(oldExpState))
      if ("E".equals(curExpState)) {
        expireDateChange = "D";
      } else {
        expireDateChange = "U";
      }
    if (checkAddExpireDate(curExpState, oldExpState, curDefaultBal, oldDefaultBal))
      expireDateChange = "A";
    return expireDateChange;
  }

  private boolean checkAddExpireDate(String curExpState, String oldExpState, BalDto curDefaultBal,
      BalDto oldDefaultBal) {
    if ("NE".equals(curExpState) && curExpState.equals(oldExpState))
      return (oldDefaultBal != null && oldDefaultBal.getExpDate() != null && curDefaultBal != null && (curDefaultBal
          .getExpDate() == null
          || DateUtil.compare(curDefaultBal.getExpDate(), oldDefaultBal
              .getExpDate()) == 2));
    return false;
  }

  public String getChargeChangeStateChange(BalanceChangeTriggerDict dict) {
    BalDto oldBalDto = dict.getOldBasicBal();
    BalDto newBalDto = dict.getNewBasicBal();
    String chargeChangeState = "";
    if (oldBalDto != null && newBalDto != null &&
        oldBalDto.getRealBal() != null && newBalDto.getRealBal() != null)
      if (oldBalDto.getRealBal().longValue() < newBalDto.getRealBal().longValue()) {
        chargeChangeState = "M";
      } else if (oldBalDto.getRealBal().longValue() > newBalDto.getRealBal().longValue()) {
        chargeChangeState = "A";
      }
    return chargeChangeState;
  }

  private String getDueStateChange(BalanceChangeTriggerDict dict, SubsDto subsDict, String postpaid,
      LocalDateTime dateNow) {
    String dueStateChange = "X";
    BalDto[] curBalDtoList = dict.getCurBalList();
    BalDto[] oldBalDtoList = dict.getOldBalList();
    Long subsCreditLimit = subsDict.getCreditLimit();
    long creditLimit = ((Long) CommonUtil.nvl(subsCreditLimit, Long.valueOf(0L))).longValue();
    boolean isCurOweFee = judgeIsOweFeeForProdStateChange(postpaid, curBalDtoList,
        Long.valueOf(creditLimit), dateNow);
    boolean isOldOweFee = judgeIsOweFeeForProdStateChange(postpaid, oldBalDtoList,
        Long.valueOf(creditLimit), dateNow);
    if (isOldOweFee ^ isCurOweFee)
      dueStateChange = isOldOweFee ? "U" : "D";
    return dueStateChange;
  }

  public boolean judgeIsOweFeeForProdStateChange(String postpaid, BalDto[] curBalDtoList, Long subsCreditLimit,
      LocalDateTime now) {
    if (CommonUtil.isEmpty((Object[]) curBalDtoList))
      return true;
    boolean ret = true;
    if (subsCreditLimit == null)
      subsCreditLimit = Long.valueOf(0L);
    // String isDebtIncludeZero =
    // BillingHelper.getStringFromConfig("ACCT.COLLECTION.IS_DEBT_INCLUDE_ZERO",
    // "");
    String isDebtIncludeZero = configItemRepository.findConfigItem("ACCT", "COLLECTION", "IS_DEBT_INCLUDE_ZERO")
        .map(ConfigItemParamProjection::getParamValue).orElse("");
    if ("Y".equals(postpaid))
      isDebtIncludeZero = "N";
    String needJudgeAcctResIds = systemParamRepository.selectSystemParam("PRECHARGE_ACCT_RES_ID");
    if (StringUtil.isEmpty(needJudgeAcctResIds)) {
      // needJudgeAcctResIds = BillingConst.getDefaultAcctResId().toString();
      needJudgeAcctResIds = systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID");
    } else {
      needJudgeAcctResIds = needJudgeAcctResIds + "," + systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID");
    }
    log.debug("needJudgeAcctResIds = [{}]", new Object[] { needJudgeAcctResIds });
    for (BalDto curBalDto : curBalDtoList) {
      if (AccountHelper.isInCommaText(needJudgeAcctResIds, curBalDto.getAcctResId().toString())) {
        long usage = getUsage(curBalDto, postpaid, subsCreditLimit);
        long limit = "Y".equals(isDebtIncludeZero) ? 0L : 1L;
        if (usage < limit) {
          ret = false;
          break;
        }
      }
    }
    return ret;
  }

  public void modProdState(Map<Long, List<Long>> subsEventToSubsListMap, BalanceChangeTriggerDict dict) {
    BalanceChangeTriggerDto balanceChangeTriggerDto = balMapper.toBalanceChangeTriggerDto(dict);
    // String isSync =
    // BillingHelper.getStringFromConfig("ACCT.PAYMENT.IS_SYNC_LIFECYCLE", "N");
    String isSync = configItemRepository.findConfigItem("ACCT", "PAYMENT", "IS_SYNC_LIFECYCLE")
        .map(ConfigItemParamProjection::getParamValue).orElse("N");
    // String channel = ConfigItemCache.instance()
    // .getString("ACCT.ACCT_BILLING.OCS_CONTACT_CHANNEL_4_DEBIT_ACTION_MOD_PROD_STATE",
    // "");
    String channel = configItemRepository
        .findConfigItem("ACCT", "ACCT_BILLING", "OCS_CONTACT_CHANNEL_4_DEBIT_ACTION_MOD_PROD_STATE")
        .map(ConfigItemParamProjection::getParamValue).orElse("");
    if ("Y".equals(isSync)) {
      for (Map.Entry<Long, List<Long>> entry : subsEventToSubsListMap.entrySet()) {
        List<Long> subsIdList = entry.getValue();
        Long subsEventId = entry.getKey();
        StateChangeDict stateChangeDict = new StateChangeDict();
        stateChangeDict.setCurBalList(dict.getCurBalList());
        // stateChangeDict.set("IS_COMMIT_MDB", dict.getString("IS_COMMIT_MDB"));
        Long procSubsId = dict.getSubsId();
        if (procSubsId != null)
          stateChangeDict.setSubsId(procSubsId);
        stateChangeDict.setSpId(dict.getSpId());
        if (subsEventId.equals(Long.valueOf(123L))) {
          stateChangeDict.setPartyType("E");
          stateChangeDict.setPartyCode("10");
        }
        stateChangeDict.setContactChannelId(dict.getContactChannelId());
        if (StringUtil.isNotEmpty(channel))
          stateChangeDict.setContactChannelId(channel);
        // stateChangeDict.serviceName = "modProdStateFromAccount";
        if (CommonUtil.isNotEmpty(subsIdList)) {
          log.debug("Begin call modProdStateFromAccount service:" + subsEventId);
          stateChangeDict.setSubsIdList(subsIdList);
          stateChangeDict.setSubsEventId(subsEventId);
          // ServiceFlow.callService(stateChangeDict);
          log.debug("end call modProdStateFromAccount service:" + subsEventId);
          processBalCreditOrder(stateChangeDict, balanceChangeTriggerDto);
          fillUpCalcFeeInfo(dict, stateChangeDict);
        }
      }
    } else {
      for (Map.Entry<Long, List<Long>> entry : subsEventToSubsListMap.entrySet()) {
        List<Long> subsIdList = entry.getValue();
        Long subsEventId = entry.getKey();
        if (CommonUtil.isNotEmpty(subsIdList)) {
          Iterator<Long> iter = subsIdList.iterator();
          while (iter.hasNext()) {
            Long subsId = iter.next();
            log.debug("Begin call modProdStateFromAccount service:" + subsEventId);
            AsynCallDto asynCallDto = new AsynCallDto();
            asynCallDto.setAsynCalId(billingSeqHelper.getBillingSeq("ASYN_CALL_ID_SEQ"));
            asynCallDto.setSubsId(subsId);
            asynCallDto.setEventId(subsEventId);
            asynCallDto.setState("A");
            StringBuffer avpBuf = new StringBuffer("");
            if (subsEventId.equals(Long.valueOf(123L)))
              avpBuf.append("493").append("=").append("10");
            if (avpBuf.length() > 0)
              avpBuf.append("|");
            avpBuf.append("1001").append("=").append(dict.getContactChannelId());
            asynCallDto.setAvp(avpBuf.toString());
            asynCallDto.setComments(null);
            // IAsynCallDAO asynCallDAO = (IAsynCallDAO) DAOFactory.create("AsynCall",
            // JdbcUtil4BL.getDbCache());
            asynCallRepository.save(asynCallMapper.toEntity(asynCallDto));
            log.debug("asyn call Dto is {}", new Object[] { asynCallDto });
            log.debug(" insertAsynCall success. ASYN_CAL_ID={}", new Object[] { asynCallDto.getAsynCalId() });
          }
        }
      }
    }
  }

  public void processBalCreditOrder(StateChangeDict triggerDict, BalanceChangeTriggerDto triggerDto) {
    log.debug("processBalCreditOrder called triggerDict is [{}] triggerDto is [{}]",
        new Object[] { triggerDict, triggerDto });
    BalCreditOrderDto[] balCreditOrderList = triggerDict.getBalCreditOrderList();
    if (balCreditOrderList == null) {
      log.debug("no balCreditOrderList to deal with");
      return;
    }
    // IBalCreditOrderDAO balCreditOrderDAO = (IBalCreditOrderDAO)
    // DAOFactory.createModuleDAO("BalCreditOrder",
    // "billing.common",
    // JdbcUtil4BL.getDbBackService());
    for (int i = 0; i < balCreditOrderList.length; i++) {
      Long balCreditOrderId = billingSeqHelper.getBillingSeq("BAL_CREDIT_ORDER_ID_SEQ");
      balCreditOrderRepository.save(balCreditOrderMapper.toEntity(balCreditOrderList[i], triggerDto, balCreditOrderId));
    }
    log.debug("processBalCreditOrder end");
  }

  private void fillUpCalcFeeInfo(BalanceChangeTriggerDict dict, StateChangeDict stateChangeDict) {
    if (stateChangeDict.getSubsEventId() != null) {
      // String subsEventIdList =
      // ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.SUBS_EVENT_ID_NEED_FEED_BACK");
      String subsEventIdList = configItemRepository
          .findConfigItem("ACCT", "ACCOUNT_PUBLIC", "SUBS_EVENT_ID_NEED_FEED_BACK")
          .map(ConfigItemParamProjection::getParamValue).orElse(null);
      if (StringUtil.isNotEmpty(subsEventIdList) &&
          CommonUtil.isInCommaText(subsEventIdList, String.valueOf(stateChangeDict.getSubsEventId()))) {
        dict.setSubsEventId(stateChangeDict.getSubsEventId());
        // dict.set("ONCE_FEE", tateChangeDict.getLong("ONCE_FEE"));
      }
    }
  }

  private long getUsage(BalDto curBalDto, String postpaid, Long subsCreditLimit) {
    long balance = 0L;
    if ("Y".equals(postpaid)) {
      balance = curBalDto.getGrossBal().longValue();
    } else {
      balance = (curBalDto.getRealBal() == null) ? 0L : curBalDto.getRealBal().longValue();
    }
    return balance + subsCreditLimit.longValue();
  }

}
