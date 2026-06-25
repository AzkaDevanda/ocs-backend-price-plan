package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalanceShareHisData {
  private Long balShareId;

  private Long dateStamp;

  private Long dailyBal;

  private Long billBal;
}
