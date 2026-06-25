package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CdrTemplateRuleMatchDto {
  private Long eventType;

  private Long contactChannelId;

  private Long paymentMethod;

  private Long subsEventId;

  private Long spId;
}
