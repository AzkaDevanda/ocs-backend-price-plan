package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DebitItemDto {
  public Long debitItemId;

  public Long acctId;

  public Long billFormatId;

  public Long charge;

  public LocalDateTime createdDate;

  public Long ratio;

  public Long spId;

  public Long reversedItemId;

  public Long reversedByItemId;

  public Long acctBookId;

  public Long loanType = Long.valueOf(1L);

  public Long commission;

  public Long volume;

  public String pricePlanCode;

  private Long commRepayWithoutTax;

  private Long commRepayTaxTsc;

  private Long commRepayTaxVat;
}
