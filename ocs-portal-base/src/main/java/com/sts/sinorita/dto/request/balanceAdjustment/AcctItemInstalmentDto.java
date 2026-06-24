package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AcctItemInstalmentDto {
  private static final long serialVersionUID = 8174847593198347008L;

  public Long instlmentId;

  public Long seq;

  public Long acctId;

  public Long subsId;

  public Long charge;

  public Long acctItemId;

  public Long billingCycleId;

  public Long acctItemTypeId;

  public String state;

  public LocalDateTime stateDate;
}
