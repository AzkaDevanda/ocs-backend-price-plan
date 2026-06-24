package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositItemPaymentData {
  private Long charge;

  private Long subsId;

  private Long depositItemId;

  private Long eventPaymentId;

  private Long paymentMethodId;

  private String paymentMethodName;
}
