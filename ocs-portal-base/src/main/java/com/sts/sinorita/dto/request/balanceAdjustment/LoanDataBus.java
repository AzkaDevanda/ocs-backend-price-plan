package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class LoanDataBus extends RechargeDataBus {
  private String loanType;

  private Long[] allowdAmountList;

  private Long loanAmount;

  private DebitBalDto debitBalDto;

  private DebitItemDto debitItemDto;

  private String[] allowdPricePlanCodeList;
}
