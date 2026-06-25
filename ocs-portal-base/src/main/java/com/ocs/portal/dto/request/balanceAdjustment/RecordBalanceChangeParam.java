package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RecordBalanceChangeParam {
  private Long operateAmount;

  private LocalDateTime operateEffDate;

  private LocalDateTime operateExpDate;

  private Long operateSeconds;

  private Long operateDays;

  private LocalDateTime newBalPreExpDate;

  private Long operateCeilLimitCharge;

  private Long sourceBalId;
}
