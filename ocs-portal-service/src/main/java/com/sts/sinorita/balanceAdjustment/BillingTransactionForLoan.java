package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.CommonUtil;

public class BillingTransactionForLoan extends BillingTransactionRecord {
  private static final long SUBS_EVENT_ID_LOAN = 252L;

  private LoanDataBus dataBus;

  private AcctBookData acctBookData;

  private LoanRequest loanReq;

  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.subsEventId = SUBS_EVENT_ID_LOAN;
    this.dataBus = (LoanDataBus) databus;
    this.acctBookData = this.dataBus.getAcctBookData();
    this.loanReq = this.dataBus.getRequest();
    this.matcher = new LoanMatcher();
    this.busiGlCodeCfgDto = this.matcher.matcherBusiInfo(this.subsEventId, this.loanReq.getDebitLoanType().toString());
    this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(this.acctBookData.getAcctBookType(), this.acctBookData.getAcctResId().toString());
    this.feeRecorder = new FeeRecorder(this, this.acctBookData);
  }

  public void recordAcctBook () {
    doRecordAcctBook(this.acctBookData);
  }

  protected BillingTransactionDto prepare () {
    BillingTransactionDto dict = new BillingTransactionDto();
    if (this.busiGlCodeCfgDto != null) {
//      dict.set("GL_CODE1", this.busiGlCodeCfgDto.getGlCode());
      dict.setGlCode1(this.busiGlCodeCfgDto.getGlCode());
//      dict.set("GL_DIRECTION1", this.busiGlCodeCfgDto.getGlDirection());
      dict.setGlDirection1(this.busiGlCodeCfgDto.getGlDirection());
//      dict.set("CHARGE1", this.dataBus.getLoanAmount().longValue() * this.busiGlCodeCfgDto.getGlCoefficient().longValue());
      dict.setCharge1(this.dataBus.getLoanAmount() * this.busiGlCodeCfgDto.getGlCoefficient());
    } else {
//      dict.set("CHARGE1", this.dataBus.getLoanAmount());
      dict.setCharge1(this.dataBus.getLoanAmount());
    }
//    dict.set("SUBS_EVENT_ID", this.subsEventId);
    dict.setSubsEventId(this.subsEventId);
//    dict.set("PARTNER_DATE", this.dataBus.getDateNow());
    dict.setPartnerDate(this.dataBus.getDateNow());
    return dict;
  }

  class LoanMatcher extends AbsGlCodeMatcher {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      for (GlCodeCfgDto glCodeCfgDto : allGlCode) {
        String loanTypes = glCodeCfgDto.getLoanType();
        if (CommonUtil.isInCommaText(loanTypes, busiId, ','))
          return glCodeCfgDto;
      }
      return null;
    }
  }
}
