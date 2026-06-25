package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DepositInstData {
  private Long eventInstId;

  private Long priceId;

  private Long depositTypeId;

  private Long charge;

  private String state;

  private LocalDateTime stateDate;

  private LocalDateTime createDate;

  private String comments;

  private Long subsId;

  private Long acctId;

  private Long spId;

  private String priceName;

  private Long reserveCharge;
}
