package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingTransactionDto;

public class BillingTransactionForTransOutIn extends BillingTransactionRecord {
  private AcctBookData acctBookData;

  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.subsEventId = Long.valueOf(144L);
    this.acctBookData = databus.getAcctBookData();
    this.matcher = AbsGlCodeMatcher.nonBusiMatcher;
    this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(this.acctBookData.getAcctBookType(), this.acctBookData.getAcctResId().toString());
    this.feeRecorder = new FeeRecorder(this, this.acctBookData);
  }

  public void recordAcctBook () {
    if (this.acctBookData == null)
      return;
    doRecordAcctBook(this.acctBookData);
  }

  protected BillingTransactionDto prepare () {
    return null;
  }
}
