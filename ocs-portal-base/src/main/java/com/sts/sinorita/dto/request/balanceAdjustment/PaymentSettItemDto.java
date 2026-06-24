package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class PaymentSettItemDto {
  public Long paymentSettId;

  public Long acctItemId;

  public Long spId;

  public Long settleCharge;

  public Long billId;
}
