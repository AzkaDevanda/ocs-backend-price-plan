package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class LoanRequest extends RechargeDataBus {
  private Long relaAcctBookId;

  private Long reverseRelaAcctBookId;

  private Long receiveAmount;

  private Long debitLoanType = Long.valueOf(1L);

  private Date operateTime;

  private String hasCommission;

  private String illimixPricePlanCode;

  private Long loanGrade;
}
