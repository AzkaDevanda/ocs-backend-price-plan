package com.sts.sinorita.dto.request.balanceAdjustment;

import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.dto.response.balanceAdjustment.BalAcctItemTypeDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BalDto {
  private Long grossBal = Long.valueOf(0L);
  private Long reserveBal = Long.valueOf(0L);
  private Long consumeBal = Long.valueOf(0L);
  private Long ratingBal = Long.valueOf(0L);
  private Long billingBal = Long.valueOf(0L);
  private Long lastBal = Long.valueOf(0L);
  private String checkMode = "0|0|0";
  private String postCheckMode = "0";
  private Long balId;
  private Long acctId;
  private Long acctResId;
  private LocalDateTime effDate;
  private LocalDateTime expDate;
  private LocalDateTime updateDate;
  private Long preBalance;
  private Long preSuttleBal;
  private LocalDateTime preExpDate;
  private Long realBal;
  private Long charge;
  private Long seconds;
  private Long ceilLimit;
  private Long ceilLimitCharge;
  private Long floorLimit;
  private Long dailyCeilLimit;
  private Long dailyFloorLimit;
  private Long priority;
  private String operationType;
  private String acctResName;
  private String acctResComments;
  private Long balCode;
  private Long routingId;
  private com.sts.sinorita.dto.request.balanceAdjustment.BalAcctItemTypeDto[] balAcctItemTypeList;
  private AcctResDto acctResDto;
  private Long changeInitBal;
  private Long initBal;
  private Long lastRecharge;
  private Long balUsed;
  private Long subsId;
  private Long effSeconds;
  private LocalDateTime preEffDate;
  private Long absExpOffset;
  private Long consumeBalCharge;

  private String refAttr;

  private Long balShareId;

  private Long varCeilLimit;

  private BalShareItemDto[] balShareItemDtoList;

  private String balFlags;

  private Long custId;
}
