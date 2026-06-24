package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class LoanRepayParam {
  private final Boolean isChargeLoanCommission = Boolean.FALSE;
  private final Boolean executeFlag = Boolean.FALSE;
  private final Boolean isLoanAmountCleared = Boolean.FALSE;
  private final Long commRepayAmount = Long.valueOf(0L);
  private final Boolean isFirstSeqLoanAmountCleared = Boolean.FALSE;
  private final Boolean isFirstSeqCommissionCleared = Boolean.FALSE;
  private Long loanRepayAmount;
  private Long loanAmount;
  private Date loanDate;
  private Boolean isCommissionCleared;
  private Boolean isLoanAndCommissionAllCleared;
  private DebitItemDto debitItemDto;
  private DebitPaymentDto debitPaymentDto;
  private Long availableAmount;
  private Long lastCommCharge;
  private Long lastCommRetCharge;
  private Long loanAmountLeft;
  private Date lastRetDate;
  private Long seq;
  private String pricePlanCode;
  private String installFlag;

  private Long commTaxTsc;

  private Long commTaxVat;

  private Long commWithoutTax;

  private Long commRepayWithoutTax;

  private Long commRepayTaxTsc;

  private Long commRepayTaxVat;
}
