package com.ocs.portal.dto.response.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AcmCycleTypeDto {
  public Long acmCycleTypeId;

  public String timeUnit;

  public Long quantity;

  public LocalDateTime beginDate;

  public String refType;

  public Long reAttr;

  public Long spId;
}
