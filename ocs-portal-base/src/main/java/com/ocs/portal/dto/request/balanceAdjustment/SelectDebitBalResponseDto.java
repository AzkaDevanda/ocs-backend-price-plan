package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SelectDebitBalResponseDto {
  private Long acctId;
  private Long bal;
  private Long spId;
  private Long commissionCharge;
  private String isCommChargeNotRet;
  private Long lastDebitCharge;
  private String loanType;
  private LocalDateTime lastDebitDate;
  private Long lastRetCharge;
  private LocalDateTime lastRetDate;
  private Long lastCommCharge;
  private Long lastCommRetCharge;
  private Long lastDebitBalId;
  private String pricePlanCode;
  private String lastButNDebitInfo;
}
