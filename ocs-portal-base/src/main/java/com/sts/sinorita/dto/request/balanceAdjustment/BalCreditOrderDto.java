package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalCreditOrderDto {

  private Long balCreditOrderId;

  private Long acctBookId;

  private Long subsOrderId;

  private String prodState;

  private Long subsId;

  private String blockReason;

  private Long spId;

}
