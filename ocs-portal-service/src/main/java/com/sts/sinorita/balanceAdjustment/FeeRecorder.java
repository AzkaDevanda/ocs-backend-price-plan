package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingTransactionDto;
import com.sts.sinorita.dto.request.balanceAdjustment.EventPaymentData;
import com.sts.sinorita.dto.request.balanceAdjustment.IBillingTransactionRecord;
import com.sts.sinorita.dto.request.balanceAdjustment.ReCcInstData;
import com.sts.sinorita.util.CommonUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class FeeRecorder {
  @Autowired
  BillingTransactionRecord owner;

  IBillingTransactionRecord.IBillingTransactionForFee[] source;

  public FeeRecorder(BillingTransactionRecord owner, AcctBookData acctBookData) {
    this.owner = owner;
    List<? extends ReCcInstData> reCcInstData = acctBookData.getReCcInstDataList();
    EventPaymentData eventPaymentData = acctBookData.getEventPaymentData();
    if (CommonUtil.isEmpty(reCcInstData) || eventPaymentData == null) {
      this.source = new IBillingTransactionRecord.IBillingTransactionForFee[0];
    } else {
      this.source = new IBillingTransactionRecord.IBillingTransactionForFee[] {
          new BillingTransactionForOnceFee(reCcInstData), new BillingTransactionForDeposit(reCcInstData),
          new BillingTransactionForInstantPayment(reCcInstData.get(0), eventPaymentData.getInstantPaymentList()),
          new BillingTransactionForBalDeduct(reCcInstData.get(0), eventPaymentData.getBalDeductDataList()),
          new BillingTransactionForNotePayment(reCcInstData.get(0), eventPaymentData.getNotePaymentList()) };
    }
  }

  public void recordFee() {
    for (int i = 0; i < this.source.length; i++) {
      if (this.source[i].hasFee())
        for (BillingTransactionDto dict : this.source[i].prepairFeeItem())
          this.owner.storeBillingTransaction(dict);
    }
  }
}
