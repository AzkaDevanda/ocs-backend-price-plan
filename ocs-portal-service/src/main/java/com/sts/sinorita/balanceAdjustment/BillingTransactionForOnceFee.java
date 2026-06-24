package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.cglib.core.Local;

public class BillingTransactionForOnceFee implements IBillingTransactionRecord.IBillingTransactionForFee {
  List<? extends ReCcInstData> reCcInstDataList;

  IBillingTransactionRecord.IGlCodeMatcher matcher;

  public BillingTransactionForOnceFee (List<? extends ReCcInstData> reCcInstDataList) {
    this.reCcInstDataList = reCcInstDataList;
    this.matcher = new OnceFeeMatcher();
  }

  public boolean hasFee () {
    return true;
  }

  public List<BillingTransactionDto> prepairFeeItem () {
    List<BillingTransactionDto> ret = new ArrayList<>();
    for (ReCcInstData reCcInstBLData : this.reCcInstDataList) {
      if (reCcInstBLData.getQryFeeParam() == null)
        continue;
      Long subsEventId = reCcInstBLData.getQryFeeParam().getSubsEventId();
      AssertUtil.isNotNull(subsEventId, "SUBS_EVENT_ID is null");
      OnceFeeInstDataDto[] onceFeeInstDataList = reCcInstBLData.getOnceFeeInstDataList();
      if (CommonUtil.isEmpty((Object[]) onceFeeInstDataList))
        continue;
      LocalDateTime eventTime = reCcInstBLData.getEventTime();
      ret.addAll(processOnceFeeInstDataList(onceFeeInstDataList, subsEventId, eventTime));
    }
    return ret;
  }

  private Collection<? extends BillingTransactionDto> processOnceFeeInstDataList (OnceFeeInstDataDto[] onceFeeInstDataList, Long subsEventId, LocalDateTime eventTime) {
    Map<Long, BillingTransactionDto> map = new HashMap<>();
    OnceFeeInstDataDto onceFeeInstData = null;
    GlCodeCfgDto onceFeeGlCode = null;
    for (int i = 0; i < onceFeeInstDataList.length; i++) {
      onceFeeInstData = onceFeeInstDataList[i];
      BillingTransactionDto dict = map.get(onceFeeInstData.getAcctId());
      if (dict == null) {
        dict = new BillingTransactionDto();
        map.put(onceFeeInstData.getAcctId(), dict);
        Long acctItemTypeId = onceFeeInstData.getAcctItemTypeId();
        if (acctItemTypeId != null)
          onceFeeGlCode = this.matcher.matcherBusiInfo(subsEventId, acctItemTypeId.toString());
        if (onceFeeGlCode != null) {
//          dict.set("GL_CODE", onceFeeGlCode.getGlCode());
          dict.setGlCode(onceFeeGlCode.getGlCode());
//          dict.set("GL_DIRECTION", onceFeeGlCode.getGlDirection());
          dict.setGlDirection(onceFeeGlCode.getGlDirection());
//          dict.add("GL_COEFFICIENT", onceFeeGlCode.getGlCoefficient());
          dict.setGlCoefficient(onceFeeGlCode.getGlCoefficient());
        }
//        dict.set("ACCT_ID", onceFeeInstData.getAcctId());
        dict.setAcctId(onceFeeInstData.getAcctId());
//        dict.set("EVENT_INST_ID", onceFeeInstData.getEventInstId());
        dict.setEventInstId(onceFeeInstData.getEventInstId());
//        dict.set("SUBS_ID", onceFeeInstData.getSubsId());
        dict.setSubsId(onceFeeInstData.getSubsId());
//        dict.set("SUBS_EVENT_ID", subsEventId);
        dict.setSubsEventId(subsEventId);
//        dict.set("PARTNER_DATE", eventTime);
        dict.setPartnerDate(eventTime);
//        dict.set("ACCT_RES_ID", onceFeeInstData.getAcctResId());
        dict.setAcctResId(onceFeeInstData.getAcctResId());
//        dict.set("CREATED_DATE", eventTime);
        dict.setCreatedDate(eventTime);
      }
//      dict.add("CHARGE_LIST", onceFeeInstData.getCharge());
      dict.getChargeList().add(onceFeeInstData.getCharge());

    }
    List<BillingTransactionDto> list = new ArrayList<>();
    for (BillingTransactionDto el : map.values())
      splitBillingTransaction(el, list);
    return list;
  }

  public void splitBillingTransaction (BillingTransactionDto input, List<BillingTransactionDto> result) {
    List<Long> charges = input.getChargeList();
    if (charges == null || charges.isEmpty()) {
      return;
    }
    Long glCoef = input.getGlCoefficient() != null ? input.getGlCoefficient() : 1L;
    for (Long charge : charges) {
      BillingTransactionDto dto = new BillingTransactionDto();
      dto.setSubsEventId(input.getSubsEventId());
      dto.setContactChannelId(input.getContactChannelId());
      dto.setReasonId(input.getReasonId());
      dto.setCreatedDate(input.getCreatedDate());
      dto.setPartnerDate(input.getPartnerDate());
      dto.setAcctId(input.getAcctId());
      dto.setEventInstId(input.getEventInstId());
      dto.setSubsId(input.getSubsId());
      dto.setAcctResId(input.getAcctResId());
      // Copy GL info
      dto.setGlCode(input.getGlCode());
      dto.setGlDirection(input.getGlDirection());
      dto.setGlCoefficient(glCoef);
      // Set charge hasil kali coefficient
      dto.setCharge1(glCoef * charge);
      result.add(dto);
    }
  }


  class OnceFeeMatcher extends AbsGlCodeMatcher {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      for (GlCodeCfgDto glCodeCfgDto : allGlCode) {
        String acctItemTypeIds = glCodeCfgDto.getAcctItemTypeId();
        if (CommonUtil.isInCommaText(acctItemTypeIds, busiId, ','))
          return glCodeCfgDto;
      }
      return null;
    }
  }
}
