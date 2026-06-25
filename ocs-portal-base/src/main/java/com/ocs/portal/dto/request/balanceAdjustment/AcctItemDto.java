package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AcctItemDto {
  public Long acctItemId;

  public Long billingCycleId;

  public Long acctId;

  public Long subsId;

  public Long acctItemTypeId;

  public Long charge;

  public Long basicCharge;

  public LocalDateTime createdDate;

  public String state;

  public LocalDateTime stateDate;

  public Long balId;

  public Long settleCharge;

  public Long spId;

  public Long acctBookId;

  public Long billId;

  public Long batchId;
  public Long gstSeq = Long.valueOf(1L);
  private Long instalmentPartialSettleAmount;
}
