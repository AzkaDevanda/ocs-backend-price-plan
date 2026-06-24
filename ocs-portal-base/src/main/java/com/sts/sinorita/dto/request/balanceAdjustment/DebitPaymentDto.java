package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class DebitPaymentDto {
  public Long acctBookId;

  public Long debitItemId;

  public String refAttr;

  public Long spId;
}
