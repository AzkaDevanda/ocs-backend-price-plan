package com.sts.sinorita.dto.request.balanceAdjustment.adjust;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SingleBalAdjust {
  private Long adjustReasonId;

  private Long balance;

  private LocalDateTime expDate;

  private String deductFlag;

  private Long charge;

  private Long acctResId;

  private String acctResCode;

  private Long balId;

  private Long seconds;

  private Long days;

  private Long months;

  private Boolean canCreateBal;

  private LocalDateTime effDate;

  private Long reversedAdjustId;
}
