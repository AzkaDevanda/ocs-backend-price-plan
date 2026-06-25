package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import lombok.Data;

import java.util.Date;

@Data
public class BalanceData {
  private Long balId;

  private Long acctId;

  private Long acctResId;

  private Long grossBal;

  private Long reserveBal;

  private Long consumeBal;

  private Long ratingBal;

  private Long billingBal;

  private Date effDate;

  private Date expDate;

  private Date updateDate;

  private Long preBalance;

  private Long preSuttleBal;

  private Date preExpDate;

  private Long realBal;

  private Long charge;

  private Long seconds;

  private Long ceilLimit;

  private Long floorLimit;

  private Long dailyCeilLimit;

  private Long dailyFloorLimit;

  private Long priority;

  private String operationType;

  private String acctResName;

  private String acctResComments;

  private Long balCode;

  private Long routingId;

  private BalAcctItemTypeDto[] balAcctItemTypeList;

  private AcctResDto acctResDto;

  private Long subsId;

  private Long effSeconds;

  private Date preEffDate;

  private Long absExpOffset;

  private Long initBal;

  private Long consumeBalCharge;

  private Long custId;
}
