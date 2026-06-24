package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class QryAcmDict {
  private Long subsId;
  private Long resourceId;
  private Long acctId;
  private Long routingId;
  private String acmType;
  private String beginDate;
  private Long billingCycleId;
  private Long acmValue;
}
