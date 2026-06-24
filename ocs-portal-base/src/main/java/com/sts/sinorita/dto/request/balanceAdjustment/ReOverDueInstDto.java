package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReOverDueInstDto {
  public Long eventInstId;

  public Long paymentSettId;

  public Long overduePlanId;

  public Long capitalAmount;

  public Long overdueDay;

  public Long spId;
}
