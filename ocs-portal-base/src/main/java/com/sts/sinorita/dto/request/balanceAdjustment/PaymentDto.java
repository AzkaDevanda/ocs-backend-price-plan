package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PaymentDto {
  public Long paymentId;

  public Long reversedPaymentId;

  public Long paymentMethodId;

  public Long bankId;

  public String voucher;

  public Long submitAmount;

  public Long returnAmount;

  public String refAttr;

  public String checkNbr;

  public String checkOwnerName;

  public LocalDateTime checkIssueDate;

  public LocalDateTime checkExpDate;

  public String scratchCardPwd;

  public String prefix;

  public String accNbr;

  public LocalDateTime paymentDate;

  public Long spId;

  public Long oriAcctResId;

  public Long destAcctResId;

  public Long destAmount;

  public Long reversedByPaymentId;

  public Long partId;

  public Long overpayAmount;

  public AcctBookDto acctBookDto;

  public Long refundReasonId;

  public String receiptNum;

  private String attachFile;
}
