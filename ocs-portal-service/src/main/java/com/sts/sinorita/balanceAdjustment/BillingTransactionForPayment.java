package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;

public class BillingTransactionForPayment extends BillingTransactionRecord {
  private AcctBookData acctBookData;

  private PaymentDto paymentDto;

  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.acctBookData = databus.getAcctBookData();
    if (databus instanceof RechargeDataBus rechargeDataBus) {
      this.paymentDto = rechargeDataBus.getPaymentDto();
    } else if (databus instanceof ReverseDataBus reverseDataBus) {
      this.paymentDto = reverseDataBus.getPaymentDto();
    }
    if (this.acctBookData.getCharge() > 0L) {
      this.subsEventId = 183L;
    } else {
      this.subsEventId = 83L;
    }
    this.matcher = AbsGlCodeMatcher.getPaymentMatcher();
    this.busiGlCodeCfgDto = this.matcher.matcherBusiInfo(this.subsEventId, this.paymentDto.getPaymentMethodId().toString());
    this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(this.acctBookData.getAcctBookType(), this.acctBookData.getAcctResId().toString());
    this.feeRecorder = new FeeRecorder(this, this.acctBookData);
  }

  public void recordAcctBook () {
    doRecordAcctBook(this.acctBookData);
  }

  protected BillingTransactionDto prepare () {
    BillingTransactionDto dict = new BillingTransactionDto();
//    dict.set("SUBS_EVENT_ID", this.subsEventId);
    dict.setSubsEventId(this.subsEventId);
//    dict.set("ACCT_BOOK_ID", this.acctBookData.getAcctBookId());
    dict.setAcctBookId(this.acctBookData.getAcctBookId());
//    dict.set("ACCT_BOOK_TYPE", this.acctBookData.getAcctBookType());
    dict.setAcctBookType(this.acctBookData.getAcctBookType());
//    dict.set("ACCT_RES_ID", this.acctBookData.getAcctResId());
    dict.setAcctResId(this.acctBookData.getAcctResId());
//    dict.set("CONTACT_CHANNEL_ID", this.acctBookData.getContactChannelId());
    dict.setContactChannelId(this.acctBookData.getContactChannelId());
//    dict.set("PAYMENT_METHOD_ID", this.paymentDto.getPaymentMethodId());
    dict.setPaymentMethodId(this.paymentDto.getPaymentMethodId());
//    dict.set("PRE_BALANCE1", this.acctBookData.getPreBalance());
    dict.setPreBalance1(this.acctBookData.getPreBalance());
    if (this.busiGlCodeCfgDto != null) {
//      dict.set("CHARGE1", this.paymentDto.getSubmitAmount().longValue() * this.busiGlCodeCfgDto.getGlCoefficient().longValue());
      dict.setCharge1(this.paymentDto.getSubmitAmount() * this.busiGlCodeCfgDto.getGlCoefficient());
//      dict.set("GL_CODE1", this.busiGlCodeCfgDto.getGlCode());
      dict.setGlCode1(this.busiGlCodeCfgDto.getGlCode());
//      dict.set("GL_DIRECTION1", this.busiGlCodeCfgDto.getGlDirection());
      dict.setGlDirection1(this.busiGlCodeCfgDto.getGlDirection());
    } else {
//      dict.set("CHARGE1", this.paymentDto.getSubmitAmount());
      dict.setCharge1(this.paymentDto.getSubmitAmount());
    }
//    dict.set("PARTNER_DATE", this.paymentDto.getPaymentDate());
    dict.setPartnerDate(this.paymentDto.getPaymentDate());
    return dict;
  }
}
