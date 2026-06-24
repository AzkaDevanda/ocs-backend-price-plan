package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DebitBalDto {
  public Long acctId;

  public Long bal;

  public Long spId;

  public Long loanType = Long.valueOf(1L);

  public Long commissionCharge;

  public String isCommChargeNotRet;

  public Long lastDebitCharge;

  public LocalDateTime lastDebitDate;

  public Long lastRetCharge;

  public Long lastCommCharge;

  public Long lastCommRetCharge;

  public Long lastDebitBalId;

  public String lastButNDebitInfo;

  public LocalDateTime lastRetDate;

  public String pricePlanCode;

  public Long seq;

  public Long commTaxTsc;

  public Long commTaxVat;

  public Long commWithoutTax;
}
