package com.ocs.portal.dto.response.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SelectBalListFilterExpireExceptRefillYResponseDto {
  List<BalAcctItemTypeDto> balAcctItemType;
  AcctResDto acctRes;
  private Long acctId;
  private Long acctResId;
  private Long grossBal;
  private Long consumeBal;
  private Long ratingBal;
  private Long billingBal;
  private LocalDateTime effDate;
  private LocalDateTime expDate;
  private LocalDateTime updateDate;
  private Long ceilLimit;
  private Long floorLimit;
  private Long dailyCeilLimit;
  private Long dailyFloorLimit;
  private Integer priority;
  private Long initBal;
  private Long balId;
  private Long balUsed;
  private Long subsId;

}
