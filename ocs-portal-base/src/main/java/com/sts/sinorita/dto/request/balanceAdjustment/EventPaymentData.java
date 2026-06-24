package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPaymentData {
  public String receiptNum;
  private Long eventPaymentId;
  private Long eventPaymentSn;
  private Long charge;
  private Long reversedEventPaymentId;
  private String partyType;
  private String partyCode;
  private Date createdDate;
  private Long spId;
  private Long acctId;
  private Long oriCharge;
  private Long reversedByPaymentId;
  private Long promotionPlanId;
  private Long discountCharge;
  private Long contactChannelId;
  private String comments;
  private Long refundReasonId;
  private Long[] eventInstIdList;
  private BalDeductData[] balDeductDataList;
  private BalDeductData[] otherBalDeductDataList;
  private InstantPaymentData[] instantPaymentList;
  private NotePaymentData[] notePaymentList;
  private DepositItemPaymentData[] depositItemPaymentDataList;
  private InstantPaymentData[] overdueInstantPaymentList;
  private Long orgId;
  private boolean fromCsr = false;
  private Long[] reverseInstIdList;
  private Long oriAcctId;
  private String rechargeInvoiceFlag;
  private String dummyRechargeFlag;
  private Long paymentMethodId;
}
