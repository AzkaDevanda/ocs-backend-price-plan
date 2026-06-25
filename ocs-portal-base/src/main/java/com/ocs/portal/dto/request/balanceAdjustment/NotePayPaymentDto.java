package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class NotePayPaymentDto {
  private Long noteId;

  private Long charge;

  private Long notePaymentMethodId;

  private Long preBalance;
}
