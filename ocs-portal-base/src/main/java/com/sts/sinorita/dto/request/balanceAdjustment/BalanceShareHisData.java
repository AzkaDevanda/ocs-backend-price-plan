package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalanceShareHisData {
  private Long balShareId;

  private Long dateStamp;

  private Long dailyBal;

  private Long billBal;
}
