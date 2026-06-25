package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BillDto {
  private Long billId;

  private String billNbr;

  private Long billingCycleId;

  private Long acctId;

  private Long preBalance;

  private Long due;

  private Long disputeCharge;

  private Long recvCharge;

  private Long pastAdjustCharge;

  private Long adjustCharge;

  private Long chargeBeAdjusted;

  private Long spId;

  private String billCode;

  private Long settCharge;

  private Long chargeBeReversed;

  private String operationType;

  private BillEx billEx;
}
