package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class CcEventTypeDto {
  public Long eventType;

  public String eventTypeName;

  public String comments;

  public String eventTypeCode;
}
