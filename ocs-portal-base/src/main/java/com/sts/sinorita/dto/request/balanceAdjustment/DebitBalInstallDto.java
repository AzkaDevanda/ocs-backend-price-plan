package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DebitBalInstallDto {
  private Long acctId;

  private Long bal;

  private Long spId;

  private Long commissionCharge;

  private Long retCharge;

  private Long commRetCharge;

  private String loanType;

  private LocalDateTime lastRetDate;

  private Long installSeq;
}
