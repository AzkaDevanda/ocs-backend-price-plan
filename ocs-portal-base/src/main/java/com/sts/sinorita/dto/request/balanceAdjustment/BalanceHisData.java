package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceHisData {
  private Long balId;

  private Long dateStamp;

  private Long dailyBal;

  private Long billBal;
}
