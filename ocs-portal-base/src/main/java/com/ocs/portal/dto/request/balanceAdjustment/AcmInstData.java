package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AcmInstData {
  private Long eventInstId;

  private Long seq;

  private Long priceVerId;

  private Long resourceId;

  private Long acmValue;

  private Long acctId;

  private Long subsId;

  private LocalDateTime cycleBeginDate;

  private LocalDateTime cycleEndDate;

  private Long billingCycleId;

  private Long spId;

  private Long dateStamp;

  private Long pricePlanId;

  private String state;

  private LocalDateTime stateDate;

  private String refAttr;

  private Long routingId;

  private String acmType;

  private Long cycleBeginDateStamp;

  private Long cycleEndDateStamp;

  private String prodIds;

  private String subsUppInstId;
}
