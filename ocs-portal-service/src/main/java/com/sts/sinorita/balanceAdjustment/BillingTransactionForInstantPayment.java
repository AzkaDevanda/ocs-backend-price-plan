package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class BillingTransactionForInstantPayment implements IBillingTransactionRecord.IBillingTransactionForFee {
  InstantPaymentData[] instantPaymentList;

  boolean hasFee = false;

  IBillingTransactionRecord.IGlCodeMatcher matcher;

  Long subsEventId = 285L;

  public BillingTransactionForInstantPayment (ReCcInstData firstReCcInstData, InstantPaymentData[] instantPaymentList) {
    if (firstReCcInstData.getQryFeeParam() != null)
      this.subsEventId = firstReCcInstData.getQryFeeParam().getSubsEventId();
    this.instantPaymentList = instantPaymentList;
    this.hasFee = CommonUtil.isNotEmpty((Object[]) instantPaymentList);
    this.matcher = AbsGlCodeMatcher.getPaymentMatcher();
  }

  public boolean hasFee () {
    return this.hasFee;
  }

  public List<BillingTransactionDto> prepairFeeItem () {
    AssertUtil.isTrue(this.hasFee);
    List<BillingTransactionDto> ret = new ArrayList<>();
    BillingTransactionDto dict = null;
    GlCodeCfgDto instantPaymentGlCode = null;
    for (InstantPaymentData instantPaymentData : this.instantPaymentList) {
      Long paymentMethodId = instantPaymentData.getPaymentMethodId();
      if (paymentMethodId != null)
        instantPaymentGlCode = this.matcher.matcherBusiInfo(this.subsEventId, paymentMethodId.toString());
      dict = new BillingTransactionDto();
      if (instantPaymentGlCode != null) {
//        dict.set("GL_CODE1", instantPaymentGlCode.getGlCode());
        dict.setGlCode1(instantPaymentGlCode.getGlCode());
//        dict.set("GL_DIRECTION1", instantPaymentGlCode.getGlDirection());
        dict.setGlDirection1(instantPaymentGlCode.getGlDirection());
//        dict.set("CHARGE1", instantPaymentData.getCharge() * instantPaymentGlCode.getGlCoefficient());
        dict.setCharge1(instantPaymentData.getCharge() * instantPaymentGlCode.getGlCoefficient());
      } else {
//        dict.set("CHARGE1", instantPaymentData.getCharge());
        dict.setCharge1(instantPaymentData.getCharge());
      }
//      dict.set("EVENT_PAYMENT_ID", instantPaymentData.getEventPaymentId());
      dict.setEventPaymentId(instantPaymentData.getEventPaymentId());
//      dict.set("SUBS_EVENT_ID", this.subsEventId);
      dict.setSubsEventId(this.subsEventId);
//      dict.set("PARTNER_DATE", instantPaymentData.getCreatedDate());
      dict.setPartnerDate(instantPaymentData.getCreatedDate());
      ret.add(dict);
    }
    return ret;
  }
}
