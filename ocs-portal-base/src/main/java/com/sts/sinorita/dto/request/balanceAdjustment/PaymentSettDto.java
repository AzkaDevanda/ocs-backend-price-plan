package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PaymentSettDto {
  public Long paymentSettId;

  public Long reversedPaymentSettId;

  public Long acctBookId;

  public Long charge;

  public LocalDateTime createdDate;

  public Long spId;

  public Long tolerance;
}
