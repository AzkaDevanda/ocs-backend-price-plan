package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class AdjustItemDto {
  private static final long serialVersionUID = 2420908732829508496L;

  public Long acctItemId;

  public Long adjustId;

  public Long refAcctItemId;

  public Long spId;

  public Long refBillId;

  public String comments;
}
