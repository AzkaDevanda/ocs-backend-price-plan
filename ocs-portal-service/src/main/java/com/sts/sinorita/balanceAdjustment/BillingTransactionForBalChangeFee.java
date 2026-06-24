package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingTransactionDto;

public class BillingTransactionForBalChangeFee extends BillingTransactionRecord {
  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.feeRecorder = new FeeRecorder(this, databus.getAcctBookData());
  }

  public void recordAcctBook () {
  }

  public void recordBusiInfo () {
  }

  protected BillingTransactionDto prepare () {
    return null;
  }
}
