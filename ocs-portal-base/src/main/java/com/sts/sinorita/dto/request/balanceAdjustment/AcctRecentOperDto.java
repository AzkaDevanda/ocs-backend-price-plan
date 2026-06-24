package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AcctRecentOperDto {
  public Long acctId;

  public String recentOper;

  public LocalDateTime updateDate;
}
