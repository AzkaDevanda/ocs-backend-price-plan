package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BillEx {
  private Long billId;

  private Long subsEventCharge;

  private String payInTime;
}
