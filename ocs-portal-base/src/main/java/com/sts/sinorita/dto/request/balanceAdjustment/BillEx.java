package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BillEx {
  private Long billId;

  private Long subsEventCharge;

  private String payInTime;
}
