package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverdueItemDto {
  public Long eventInstId;

  public Long seq;

  public Long billingCycleId;

  public Long acctItemTypeId;

  public Long charge;

  public Long overdueCharge;

  public Long overdueAdjustId;

  public Long adjustCharge;

  public Long spId;
}
