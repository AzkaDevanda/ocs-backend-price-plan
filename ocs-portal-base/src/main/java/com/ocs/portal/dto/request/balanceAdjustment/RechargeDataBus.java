package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RechargeDataBus extends BillingBaseDataBus {
  public Boolean isAsynPayment = Boolean.valueOf(false);
  private Boolean isCheckMaxRechargeAmount = Boolean.valueOf(true);
  private Boolean isAllowInactive = Boolean.valueOf(true);
  private Boolean isAllowLost = Boolean.valueOf(false);
  private Boolean isAllowRechargeBlack = Boolean.valueOf(false);
  private Boolean isCheckPwd = Boolean.valueOf(false);

  private Boolean isNewConnection = Boolean.valueOf(false);

  private PaymentDto paymentDto;

  private Long overdue;

  private Long submitAmount;

  private Long returnAmount;

  private Date rechargeTime;

  private String voucher;

  private Long bankId;

  private String checkNbr;

  private String checkOwnerName;

  private String checkIssueDate;

  private String checkExpDate;

  private String scratchCardPwd;

  private String refAttr;

  private Long oriAcctResId;

  private Long destAcctResId;

  private Long destAmount;

  private Boolean isCheckMinRechargeAmount;

  private Long prolongDays;

  private String cardType;

  private Long refundReasonId;

  private String bonusPaymentFlag;
}
