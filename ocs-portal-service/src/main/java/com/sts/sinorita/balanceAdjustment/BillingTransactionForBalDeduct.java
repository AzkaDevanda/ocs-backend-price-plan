package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class BillingTransactionForBalDeduct implements IBillingTransactionRecord.IBillingTransactionForFee {
  BalDeductData[] balDeductDataList;

  boolean hasFee = false;

  IBillingTransactionRecord.IGlCodeMatcher matcher;

  Long subsEventId = 285L;

  public BillingTransactionForBalDeduct (ReCcInstData firstReCcInstData, BalDeductData[] balDeductDataList) {
    if (firstReCcInstData.getQryFeeParam() != null)
      this.subsEventId = firstReCcInstData.getQryFeeParam().getSubsEventId();
    this.balDeductDataList = balDeductDataList;
    this.hasFee = CommonUtil.isNotEmpty((Object[]) balDeductDataList);
    this.matcher = AbsGlCodeMatcher.nonBusiMatcher;
  }

  public boolean hasFee () {
    return this.hasFee;
  }

  public List<BillingTransactionDto> prepairFeeItem () {
    AssertUtil.isTrue(this.hasFee);
    List<BillingTransactionDto> ret = new ArrayList<>();
    BillingTransactionDto dict = null;
    AcctBookDto deductAcctBookDto = null;
    GlCodeCfgDto balDeductGlCode = null;
    for (BalDeductData balDeductData : this.balDeductDataList) {
      deductAcctBookDto = balDeductData.getDeductAcctBookDto();
      balDeductGlCode = this.matcher.matcherAcctBook(deductAcctBookDto.getAcctBookType(), deductAcctBookDto.getAcctResId().toString());
      dict = new BillingTransactionDto();
      if (balDeductGlCode != null) {
//        dict.set("GL_CODE1", balDeductGlCode.getGlCode());
        dict.setGlCode1(balDeductGlCode.getGlCode());
//        dict.set("GL_DIRECTION1", balDeductGlCode.getGlDirection());
        dict.setGlDirection1(balDeductGlCode.getGlDirection());
//        dict.set("CHARGE1", deductAcctBookDto.getCharge().longValue() * balDeductGlCode.getGlCoefficient().longValue());
        dict.setCharge1(deductAcctBookDto.getCharge() * balDeductGlCode.getGlCoefficient());
      } else {
//        dict.set("CHARGE1", deductAcctBookDto.getCharge());
        dict.setCharge1(deductAcctBookDto.getCharge());
      }
//      dict.set("ACCT_BOOK_ID", deductAcctBookDto.getAcctBookId());
      dict.setAcctBookId(deductAcctBookDto.getAcctBookId());
//      dict.set("SUBS_EVENT_ID", this.subsEventId);
      dict.setSubsEventId(this.subsEventId);
//      dict.set("PARTNER_DATE", deductAcctBookDto.getCreatedDate());
      dict.setPartnerDate(deductAcctBookDto.getCreatedDate());
//      dict.set("EVENT_PAYMENT_ID", deductAcctBookDto.getEventPaymentId());
      dict.setEventPaymentId(deductAcctBookDto.getEventPaymentId());
      ret.add(dict);
    }
    return ret;
  }
}
