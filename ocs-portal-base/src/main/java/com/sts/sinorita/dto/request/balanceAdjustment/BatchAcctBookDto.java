package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchAcctBookDto {
  private Long abBatchId;

  private Long acctBookId;

  private Long spId;

  private Long partId;
}
