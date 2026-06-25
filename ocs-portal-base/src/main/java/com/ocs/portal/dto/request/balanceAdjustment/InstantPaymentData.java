package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstantPaymentData {
  private Long paymentId;

  private String partyType;

  private String partyCode;

  private Long paymentMethodId;

  private Long reversedPaymentId;

  private String voucher;

  private Long submitAmount;

  private Long returnAmount;

  private String reversedReason;

  private Long eventPaymentId;

  private Long bankId;

  private String checkNbr;

  private String checkOwnerName;

  private String checkIssueDate;

  private String checkExpDate;

  private Long spId;

  private LocalDateTime createdDate;

  private Long charge;

  private String paymentMethodName;

  private Long oriAcctResId;

  private Long destAcctResId;

  private Long destAmount;

  private String refAttr;

  private String orangeMoneyFlag;

  private Long contactChannelId;

  private String attachFile;
}
