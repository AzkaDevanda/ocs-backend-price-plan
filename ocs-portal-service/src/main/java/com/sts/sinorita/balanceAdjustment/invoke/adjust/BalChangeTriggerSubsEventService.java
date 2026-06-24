package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sts.sinorita.balanceAdjustment.invoke.add.ValidateBeforeDataStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.AcctAttrValueDto;
import com.sts.sinorita.dto.AdviceDict;
import com.sts.sinorita.dto.AsynCallDto;
import com.sts.sinorita.dto.BalTriggerParam;
import com.sts.sinorita.dto.DealSendMsg2MCCMDto;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.OfferDto;
import com.sts.sinorita.dto.PricePlanExDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ProdDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ReCcInstData;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.response.attr.SelectAttrCatgResponseDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.enums.EventCodeDef;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingCache;
import com.sts.sinorita.mapper.attr.AttrCatgMapper;
import com.sts.sinorita.mapper.attr.AttrMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AsynCallMapper;
import com.sts.sinorita.mapper.balanceAdjustment.ProdMapper;
import com.sts.sinorita.mapper.offer.OfferMapper;
import com.sts.sinorita.mapper.pricePlan.PricePlanMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.projection.pricePlan.SortPricePlanIdByPriorityProjection;
import com.sts.sinorita.repository.AcctAttrValueRepository;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.repository.AsynCallRepository;
import com.sts.sinorita.repository.BalPcrfRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.DynReAttrRepository;
import com.sts.sinorita.repository.OfferRepository;
import com.sts.sinorita.repository.PricePlanRepository;
import com.sts.sinorita.repository.ProdRepository;
import com.sts.sinorita.repository.SubsUppInstRepository;
import com.sts.sinorita.util.CollectionUtil;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;
import com.sts.sinorita.util.ValidateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class BalChangeTriggerSubsEventService {

  private final ValidateBeforeDataStoreService validateBeforeDataStoreService;

    private final AcctAttrValueRepository acctAttrValueRepository;

  private final DynReAttrRepository dynReAttrRepository;

  private final AttrCatgMapper attrCatgMapper;

  private final AttrMapper attrMapper;

  private final AcctResRepository acctResRepository;

  private final AsynCallRepository asynCallRepository;

  private final AsynCallMapper asynCallMapper;

  private final BalPcrfRepository balPcrfRepository;

  private final OfferRepository offerRepository;

  private final OfferMapper offerMapper;

  private final ConfigItemRepository configItemRepository;

  private final ProdRepository prodRepository;

  private final PricePlanRepository pricePlanRepository;

  private final PricePlanMapper pricePlanMapper;

  private final SubsUppInstRepository subsUppInstRepository;

  private final ProdMapper prodMapper;

  private final BillingCache billingCache;

  private AcctDto acct;

  private SubsDto subs;

  private BalDto[] oldBalList;

  private BalDto[] updateBalList;

  private String comments;

  private Long spId;

  private String acctBookType;

  private List<ReCcInstData> reCcInstDataList;

  private BalDto[] newBalList;

  private String validateFlag;

  private static final String FALSE = "N";

  private Boolean isSendBalTriggerAdvice;

  private Long eventInstId = null;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    if (StringUtil.isNotEmpty(this.acctBookType) && "H".equals(this.acctBookType)) {
      String isBalAdjustNeedBalChangeTrigger = configItemRepository.findConfigItem("ACCT", "BALAJUST",
          "IS_BAL_ADJUST_NEED_BAL_CHANGE_TRIGGER").map(ConfigItemParamProjection::getParamValue).orElse("N");
      if ("N".equals(isBalAdjustNeedBalChangeTrigger))
        return;
    }
    if ("Y".equals(this.validateFlag))
      return;
    if (CommonUtil.isEmpty((Object[]) this.updateBalList))
      return;
    Long[] pricePlanArr = initAndValidate();
    if (CommonUtil.isEmpty((Object[]) pricePlanArr))
      return;
    recordAsynCall(pricePlanArr);
    validateBeforeDataStoreService.invoke();
  }

  public Long[] initAndValidate() {
    ValidateUtil.notNull(this.acct, "acct");
    ValidateUtil.notNull(this.oldBalList, "oldBalList");
    ValidateUtil.notNull(this.updateBalList, "updateBalList");
    if (this.subs == null)
      if (CommonUtil.isNotEmpty((Object[]) this.acct.getAllSubsList())) {
        this.subs = this.acct.getAllSubsList()[0];
        if (this.subs.getProd() == null)
          this.subs.setProd(prodRepository.findProdBySubsId(this.subs.getSubsId())
              .map(prodMapper::toProdDtoFromFindProdBySubsId).orElse(null));
      } else {
        log.debug("Bal change tigger will return, because subs is null.");
        return null;
      }
    Long[] allPP = getAllPricePlan();
    List<BalDto> operatorList = BalHelper.mergeAddList(this.updateBalList);
    this.updateBalList = operatorList.<BalDto>toArray(new BalDto[0]);
    return allPP;
  }

  private Long[] getAllPricePlan() {
    Long pricePlanId = getPricePlanIdInUsed();
    Long[] pricePlanIdList = qrySubsPricePlanIdList(this.subs.getSubsId(), pricePlanId);
    if (pricePlanId == null)
      return pricePlanIdList;
    if (CommonUtil.isEmpty((Object[]) pricePlanIdList))
      return new Long[] { pricePlanId };
    Long[] ret = new Long[pricePlanIdList.length + 1];
    ret[0] = pricePlanId;
    System.arraycopy(pricePlanIdList, 0, ret, 1, pricePlanIdList.length);
    return ret;
  }

  private Long getPricePlanIdInUsed() {
    if (CommonUtil.isEmpty(this.reCcInstDataList))
      return null;
    for (ReCcInstData reCcInstData : this.reCcInstDataList) {
      Map[] arrayOfMap = reCcInstData.getReponseParamSplitResult();
      this.eventInstId = reCcInstData.getEventInstId();
      if (CommonUtil.isEmpty((Object[]) arrayOfMap))
        continue;
      for (Map<String, String> paramItem : arrayOfMap) {
        String pricePlanIdCac = paramItem.get("897");
        if (StringUtil.isNotEmpty(pricePlanIdCac))
          return Long.valueOf(pricePlanIdCac);
      }
    }
    return null;
  }

  public Long[] qrySubsPricePlanIdList(Long subsId, Long ignorePPId) {
    List<Long> pricePlanIdList = new ArrayList<>();
    try {
      Long[] defPricePlanArray = pricePlanRepository.qrySystemDefPricePlan(this.spId)
          .toArray(new Long[0]);
      if (CommonUtil.isNotEmpty((Object[]) defPricePlanArray))
        pricePlanIdList.addAll(Arrays.asList(defPricePlanArray));
      Long[] uppPricePlanArray = this.subsUppInstRepository.qryUppPricePlanBySubsId(subsId)
          .toArray(new Long[0]);
      if (CommonUtil.isNotEmpty((Object[]) uppPricePlanArray))
        pricePlanIdList.addAll(Arrays.asList(uppPricePlanArray));
    } catch (Exception ex) {
      log.debug("prefix is [{}], service number is [{}].",
          new Object[] { this.subs.getPrefix(), this.subs.getAccNbr() });
      // ExceptionHandler.publish("BL-S-ACT-00056", 0, ex);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00056"));

    }
    if (pricePlanIdList.contains(ignorePPId))
      pricePlanIdList.remove(ignorePPId);
    if (pricePlanIdList.isEmpty())
      return null;
    String allPPIds = StringUtil.toCommaString(pricePlanIdList.<Long>toArray(new Long[pricePlanIdList.size()]));
    Long[] sortedPP = this.pricePlanRepository.sortPricePlanIdByPriority(allPPIds).stream()
        .map(SortPricePlanIdByPriorityProjection::getPricePlanId).toArray(Long[]::new);
    return sortedPP;
  }

  public void recordAsynCall(Long[] pricePlanArr) {
    BalDto updateBal = null;
    BalDto oldBal = null;
    BalDto newBal = null;
    int triggerType = 0;
    long oldRealBal = 0L;
    long updateRealBal = 0L;
    BalDto defaultBalDto = BalHelper.getDefaultBal(this.oldBalList);
    BalDto newDefaultBalDto = BalHelper.getDefaultBal(this.newBalList);
    for (int i = 0; i < this.updateBalList.length; i++) {
      updateBal = this.updateBalList[i];
      oldBal = BalHelper.getBalFromListByBalId(this.oldBalList, updateBal.getBalId());
      if (oldBal == null) {
        oldRealBal = 0L;
      } else {
        oldRealBal = oldBal.getRealBal().longValue();
      }
      newBal = BalHelper.getBalFromListByBalId(this.newBalList, updateBal.getBalId());
      if (newBal == null) {
        updateRealBal = 0L;
      } else {
        updateRealBal = newBal.getRealBal().longValue();
      }
      if (!"M".equals(updateBal.getOperationType()) || oldBal != null) {
        if (updateRealBal < oldRealBal) {
          triggerType = 2;
        } else {
          triggerType = 1;
        }
        log.debug("triggerType = [{}] updateRealBal is [{}] oldRealBal is [{}]:",
            new Object[] { Integer.valueOf(triggerType), Long.valueOf(updateRealBal),
                Long.valueOf(oldRealBal) });
        List<BalTriggerParam> balTriggerParamList = null;
        try {
          balTriggerParamList = getTriggerRule(pricePlanArr, updateBal.getAcctResId(), triggerType, updateBal
              .getAcctId());
          if (CommonUtil.isEmpty(balTriggerParamList)) {
            log.debug(" balSubsEventArr is null. pricePlanId=[{}],acctResId=[{}],triggerType=[{}]",
                new Object[] { pricePlanArr, updateBal
                    .getAcctResId(), StringUtil.toString(triggerType) });
          } else {
            for (BalTriggerParam param : balTriggerParamList) {
              log.debug("balTriggerParam balThresholdId=[{}], subsEventId=[{}],pricePlanId=[{}]",
                  new Object[] { param
                      .getBalThresholdId(),
                      CommonUtil.isEmpty(param.getSubsEventList()) ? ""
                          : param
                              .getSubsEventList().toString(),
                      param.getPricePlanId() });
              Long threshold = getRealThreshold(param, oldBal);
              if (threshold == null)
                continue;
              if (isNeedTrigger(threshold.longValue(), updateRealBal, oldRealBal)) {
                dealEvent(param, updateBal);
                dealSendAdvice(param, defaultBalDto, newDefaultBalDto);
                dealSendMsg2MCCM(threshold, updateBal);
                continue;
              }
              log.debug("no need to trigger: updateRealBal is [{}] oldRealBal is [{}]",
                  new Object[] { Long.valueOf(updateRealBal),
                      Long.valueOf(oldRealBal) });
            }
          }
        } catch (Exception e) {
          log.warn("Failed to 'insertAsynCall' ", e);
        }
      }
    }
  }

  public List<BalTriggerParam> getTriggerRule(Long[] pricePlanArr, Long acctResId, int triggerType, Long acctId) {
    List<BalTriggerParam> balTriggerParamList = null;
    for (Long pricePlanId : pricePlanArr) {
      balTriggerParamList = billingCache.getBalTriggerRuleParam(pricePlanId, acctResId, Integer.toString(triggerType));
      if (CommonUtil.isNotEmpty(balTriggerParamList)) {
        dealThreshold(balTriggerParamList);
        break;
      }
    }
    return balTriggerParamList;
  }

  private void dealThreshold(List<BalTriggerParam> balTriggerList) {
    if (CommonUtil.isEmpty(balTriggerList))
      return;
    for (BalTriggerParam balTrigger : balTriggerList) {
      if (StringUtil.isNotEmpty(balTrigger.getReAttr())) {
        Long threshold = getThreshold(this.acct.getAcctId(), balTrigger.getReAttr());
        if (threshold != null)
          balTrigger.setThreshold(threshold);
      }
    }
  }

  private void dealEvent(BalTriggerParam param, BalDto updateBal) {
    if (CollectionUtil.isEmpty(param.getSubsEventList()) || param.getBalThresholdId() == null)
      return;
    for (BalTriggerParam.SubsEvent subEvent : param.getSubsEventList()) {
      dealPorPP(subEvent);
      dealReAuthorization(subEvent, updateBal, param.getBalThresholdId());
    }
  }

  private void dealSendAdvice(BalTriggerParam param, BalDto defaultBalDto, BalDto newDefaultBalDto) {
    log.debug("isSendBalTriggerAdvice=[{}]", new Object[] { this.isSendBalTriggerAdvice });
    if (CommonUtil.isEmpty(param.getAdviceList()) || (this.isSendBalTriggerAdvice != null &&
        !this.isSendBalTriggerAdvice.booleanValue()))
      return;
    AdviceDict adviceDict = new AdviceDict();
    adviceDict.setPrefix(this.subs.getPrefix());
    adviceDict.setAccNbr(this.subs.getAccNbr());
    adviceDict.setSubsId(this.subs.getSubsId());
    adviceDict.setAcctId(this.acct.getAcctId());
    adviceDict.setStateSet(this.subs.getProd().getBlockReason());
    adviceDict.setBalanceExpDate(defaultBalDto.getExpDate());
    adviceDict.setCurrentBalance(defaultBalDto.getRealBal());
    adviceDict.setAfterBalanceExpDate(newDefaultBalDto.getExpDate());
    adviceDict.setAfterBalance(newDefaultBalDto.getRealBal());
    adviceDict.setSpId(this.spId);
    String prodSpecCode = qryProdSpecCode();
    adviceDict.setProdSpecCode(prodSpecCode);
    // adviceDict.serviceName = "AdviceService";
    addSubsEventParam(adviceDict, param);
    for (BalTriggerParam.Advice advice : param.getAdviceList()) {
      // adviceDict.remove("ADVICE_ID");
      // adviceDict.set("ADVICE_TYPE", advice.getAdviceType());
      // ServiceFlow.callService(adviceDict, true);
    }
  }

  private String qryProdSpecCode() {
    String prodSpecCode = "";
    ProdDto prod = this.subs.getProd();
    if (prod == null)
      return prodSpecCode;
    if (StringUtil.isEmpty(prod.getOfferCode()))
      prod = prodRepository.selectProdDtoBySubsId(this.subs.getSubsId())
          .map(prodMapper::toProdDtoFromSelectProdDtoBySubsId).orElse(null);
    prodSpecCode = prod.getOfferCode();
    return prodSpecCode;
  }

  private void addSubsEventParam(AdviceDict adviceDict, BalTriggerParam param) {
    if (CommonUtil.isNotEmpty(param.getSubsEventList()))
      for (BalTriggerParam.SubsEvent subsEvent : param.getSubsEventList()) {
        Map<String, String> extAttrMap = StringUtil.splitPara(subsEvent.getExtAttr(), "\\|");
        if (extAttrMap == null)
          continue;
        dealOfferDto(adviceDict, extAttrMap, subsEvent.getSubsEventId());
        dealPricePlanExDto(adviceDict, extAttrMap, subsEvent.getSubsEventId());
      }
  }

  private void dealOfferDto(AdviceDict adviceDict, Map<String, String> extAttrMap, Long subsEventId) {
    String prodId = extAttrMap.get("972");
    if (StringUtil.isEmpty(prodId))
      prodId = extAttrMap.get("961");
    String days = extAttrMap.get("1044");
    if (StringUtil.isEmpty(prodId))
      return;
    OfferDto offerDto = offerRepository.selectProdSpecByProdId(Long.valueOf(prodId))
        .map(offerMapper::toOfferDtoFromSelectProdSpecByProdId).orElse(null);
    if (offerDto == null)
      return;
    if (StringUtil.isNotEmpty(adviceDict.getProdSpecName())) {
      log.debug("The 'PROD_SPEC_NAME' has existed and will not be recorded second times.");
      return;
    }
    adviceDict.setProdSpecName(offerDto.getOfferName());
    switch (subsEventId.intValue()) {
      case 81:
      case 151:
        adviceDict.setProdSpecEffDate(DateUtil.GetDBDateTime());
        if (StringUtil.isNotEmpty(days))
          adviceDict.setProdSpecEffDate(
              DateUtil.offsetDay(DateUtil.GetDBDateTime(), Integer.valueOf(days).intValue()));
        break;
    }
  }

  private void dealPricePlanExDto(AdviceDict adviceDict, Map<String, String> extAttrMap, Long subsEventId) {
    String pricePlanCode = extAttrMap.get("982");
    String days = extAttrMap.get("1044");
    if (StringUtil.isEmpty(pricePlanCode))
      return;
    PricePlanExDto pricePlanExDto = pricePlanRepository.selectPricePlanByPricePlanCode(pricePlanCode)
        .map(pricePlanMapper::toPricePlanExDtoFromSelectPricePlanByPricePlanCode).orElse(null);
    if (pricePlanExDto == null)
      return;
    if (StringUtil.isNotEmpty(adviceDict.getPricePlanName())) {
      log.debug("The 'PRICE_PLAN_NAME' has been existed and will not be recorded second times.");
      return;
    }
    adviceDict.setPricePlanName(pricePlanExDto.getPricePlanName());
    switch (subsEventId.intValue()) {
      case 2:
      case 237:
        adviceDict.setPricePlanEffDate(DateUtil.GetDBDateTime());
        if (StringUtil.isNotEmpty(days))
          adviceDict.setPricePlanExpDate(
              DateUtil.offsetDay(DateUtil.GetDBDateTime(), Integer.valueOf(days).intValue()));
        break;
    }
  }

  private Long getRealThreshold(BalTriggerParam param, BalDto oldBal) {
    Long threshold = param.getThreshold();
    if (param.getRatio() != null) {
      Long initBal = null;
      if (oldBal != null)
        initBal = oldBal.getInitBal();
      if (initBal != null) {
        BigDecimal initBalMath = BigDecimal.valueOf(initBal.longValue());
        BigDecimal ratioMath = BigDecimal.valueOf(param.getRatio().longValue());
        BigDecimal unitMath = BigDecimal.valueOf(100L);
        threshold = Long.valueOf(initBalMath.multiply(ratioMath).divide(unitMath, 0, 4).longValue());
      } else {
        threshold = null;
      }
    }
    return threshold;
  }

  private boolean isNeedTrigger(long threshold, long updateRealBal, long oldRealBal) {
    boolean needTrigger = Boolean.FALSE.booleanValue();
    if ((threshold >= updateRealBal && threshold < oldRealBal)
        || (threshold > oldRealBal && threshold <= updateRealBal))
      needTrigger = Boolean.TRUE.booleanValue();
    return needTrigger;
  }

  private void dealPorPP(BalTriggerParam.SubsEvent subEvent) {
    if (subEvent.getSubsEventId() != null && 382 != subEvent.getSubsEventId().intValue())
      addAsynCall(subEvent.getSubsEventId(), subEvent.getExtAttr());
  }

  private void dealReAuthorization(BalTriggerParam.SubsEvent subEvent, BalDto updateBal, Long balThresholdId) {
    List<Long> list = null;
    try {
      list = balPcrfRepository.selectBalPCRF(balThresholdId);
    } catch (Exception e) {
      log.warn("Find nothing by BalThresholdId", e);
    }
    if (subEvent.getSubsEventId() != null && 382 == subEvent.getSubsEventId().intValue()) {
      StringBuffer buffer = new StringBuffer("3=");
      buffer.append(DateUtil.date2String(DateUtil.GetDBDateTime(), "yyyyMMddHHmmss"));
      buffer.append("|").append("1000=").append(this.acct.getRoutingId());
      buffer.append("|").append("1370=");
      if (CommonUtil.isNotEmpty(list)) {
        buffer.append("1");
      } else {
        buffer.append("0");
      }
      // String acctResIdsForBalTrigger = ConfigItemCache.instance()
      // .getString("CUSTOMER_CARE.ASYN_CALL.ACCT_RES_ID_FOR_BAL_TRIGGER");
      String acctResIdsForBalTrigger = configItemRepository
          .findConfigItem("CUSTOMER_CARE", "ASYN_CALL", "ACCT_RES_ID_FOR_BAL_TRIGGER")
          .map(ConfigItemParamProjection::getParamValue).orElse(null);
      Long acctResId = updateBal.getAcctResId();
      if (isInCommaText(acctResIdsForBalTrigger, acctResId.toString()) && "M"
          .equals(updateBal.getOperationType())) {
        buffer.append("1");
      } else {
        buffer.append("0");
      }
      String avp = buffer.toString();
      addAsynCallExt(subEvent.getSubsEventId(), avp);
    }
  }

  private boolean isInCommaText(String commaText, String subtext) {
    if (commaText == null || subtext == null)
      return false;
    int len = subtext.length();
    if (len > commaText.length())
      return false;
    int index = commaText.indexOf(subtext, 0);
    while (index >= 0) {
      if ((index == 0 || commaText.charAt(index - 1) == ',')
          && (index + len >= commaText.length() || commaText.charAt(index + len) == ','))
        return true;
      if (index + len >= commaText.length())
        break;
      index = commaText.indexOf(subtext, index + len + 1);
    }
    return false;
  }

  private void addAsynCall(Long subsEventId, String avp) {
    AsynCallDto asynCallDto = new AsynCallDto();
    // asynCallDto.setAsynCalId(BillingSeqHelper.getBillingSeqNpoPrefix("ASYN_CALL_ID_SEQ"));
    asynCallDto.setSubsId(this.subs.getSubsId());
    asynCallDto.setEventId(subsEventId);
    asynCallDto.setState("A");
    asynCallDto.setAvp(avp);
    if (this.comments != null) {
      asynCallDto.setComments(this.comments);
    } else {
      asynCallDto.setComments(null);
    }
    // IAsynCallDAO asynCallDAO = (IAsynCallDAO) DAOFactory.create("AsynCall",
    // JdbcUtil4BL.getDbCache());
    // asynCallDAO.insertAsynCall(asynCallDto);
    asynCallRepository.save(asynCallMapper.toEntity(asynCallDto));
    log.debug(" insertAsynCall success. ASYN_CAL_ID=[{}]", new Object[] { asynCallDto.getAsynCalId() });
  }

  private void addAsynCallExt(Long subsEventId, String avp) {
    AsynCallDto asynCallDto = new AsynCallDto();
    // asynCallDto.setAsynCalId(AsynCallHelper.getAsynCallIdSeq());
    asynCallDto.setSubsId(this.subs.getSubsId());
    asynCallDto.setEventId(subsEventId);
    asynCallDto.setState("A");
    asynCallDto.setAvp(avp);
    if (this.comments != null) {
      asynCallDto.setComments(this.comments);
    } else {
      asynCallDto.setComments(null);
    }
    // IAsynCallDAO asynCallDAO = (IAsynCallDAO) DAOFactory.create("AsynCall",
    // AsynCallHelper.getDbIdentifier());
    asynCallRepository.save(asynCallMapper.toEntity(asynCallDto));
    log.debug(" insertAsynCall success. ASYN_CAL_ID=[{}]", new Object[] { asynCallDto.getAsynCalId() });
  }

  private void dealSendMsg2MCCM(Long threshold, BalDto updateBal) {
    log.info("dealSendMsg2MCCM begin");
    if (!"Q".equals(this.acctBookType)) {
      log.info("no once fee deal, return.");
      return;
    }
    try {
      AcctResDto accResDto = acctResRepository.selectAcctRes(updateBal.getAcctResId()).get();
      DealSendMsg2MCCMDto dict = new DealSendMsg2MCCMDto();
      dict.setEventInstId(this.eventInstId);
      dict.setAcctId(updateBal.getAcctId());
      dict.setSubsId((this.subs == null) ? null : this.subs.getSubsId());
      dict.setBalId(updateBal.getBalId());
      dict.setBonusAmount("");
      dict.setThresholdType("3");
      dict.setNotifyType("1");
      dict.setNotifyTime(DateUtil.GetDBDateTime());
      dict.setAcctResId((accResDto != null) ? accResDto.getAcctResId() : null);
      dict.setThreshold(threshold);
      dict.setEventCode(EventCodeDef.LOWBALANCE.toString());
      dict.setOfferId((this.subs == null) ? null : this.subs.getProd().getOfferId());
      dict.setIsCurrency((accResDto != null) ? accResDto.getIsCurrency() : null);
      // String msg = MCCMMsgHelper.getInfoString(dict, BillingSeqHelper.getBillingSeq("TO_MCCM_SEQ"));
      // MccmSocketMessageReceiver.receiveMessageToQueue(msg);
    } catch (Exception e) {
      log.warn("Fail to send message to MCCM.", e);
    }
    log.info("dealSendMsg2MCCM end");
  }

  public Long getThreshold(Long acctId, String reAttr) {
    Long threshold = null;
    try {
      SelectAttrCatgResponseDto catg = dynReAttrRepository.selectAttrCatg(reAttr)
          .map(attrCatgMapper::toSelectAttrCatgResponseDto)
          .orElse(null);
      String attrCatg = null;
      Long attrId = null;
      if (catg != null) {
        attrCatg = catg.getAttrCatg();
        attrId = catg.getAttrId();
      }
      if (StringUtil.isNotEmpty(attrCatg) && attrId != null &&
          "D".equals(attrCatg)) {
        AcctAttrValueDto acctAttrValueDto = acctAttrValueRepository.selectAcctAttrValue(acctId, attrId)
            .map(attrMapper::toAcctAttrValueDto).orElse(null);
        if (acctAttrValueDto != null && StringUtil.isNotEmpty(acctAttrValueDto.getAttrValue()))
          threshold = Long.valueOf(acctAttrValueDto.getAttrValue());
      }
    } catch (Exception e) {
      log.warn("Failed to 'selectAttrCatg'", e);
    }
    return threshold;
  }

}
