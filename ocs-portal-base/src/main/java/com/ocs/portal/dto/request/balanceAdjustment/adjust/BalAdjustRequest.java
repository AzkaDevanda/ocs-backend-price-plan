package com.ocs.portal.dto.request.balanceAdjustment.adjust;

import java.time.LocalDateTime;

import com.ocs.portal.dto.request.balanceAdjustment.BillingRequest;

import lombok.Data;

@Data
public class BalAdjustRequest extends BillingRequest {
  private Long acctItemTypeId;

  private String comments;

  private Long adjustReasonId;

  private Long balance;

  private LocalDateTime effDate;

  private LocalDateTime expDate;

  private Long days;

  private Long balanceCalcFlag;

  private Boolean canCreateBal;

  private Long expDateExtendFlag;

  private String partnerSnOld;

  private Long reversedAdjustId;

  private Boolean canModifyEffDate;

  private String deductFlag;

  private Boolean isLimitMaxCharge;

  private Boolean isLimitMaxDays;

  private LocalDateTime suspendStopDate;

  private Long suspendExtention;

  private Long months;

  private SingleBalAdjust[] singleBalAdjustList;

  private String balAdjustTriggerSource;

  private Long ceilLimit;
}
