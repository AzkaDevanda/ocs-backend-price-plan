package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class DisputeDto {
  // private static final long serialVersionUID = -4059513626507108344L;

  public Long disputeId;

  public Long processedDisputeId;

  public String comments;

  public Long spId;
}
