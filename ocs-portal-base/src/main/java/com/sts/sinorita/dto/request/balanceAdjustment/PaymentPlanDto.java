package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentPlanDto {
  public Long paymentPlanId;

  public Long acctId;

  public Long dueAmount;

  public Long settCharge;

  public Date dueDate;

  public Date createdDate;

  public Long billId;

  public String planStatus;

  public Long installmentId;

  public Long seq;

  public String installStatus;

  public Date installStatusDate;

  public Long spId;

  public Long taxCharge;

  public Long settTaxCharge;

  public String billNbr;

}
