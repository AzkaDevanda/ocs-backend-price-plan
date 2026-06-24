package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class AdjustDto {
  public Long adjustId;

  public Long adjustReasonId;

  public String comments;

  public Long reversedAdjustId;

  public Long spId;

  public Long subsId;
}
