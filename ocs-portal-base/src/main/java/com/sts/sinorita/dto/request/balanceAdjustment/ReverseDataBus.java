package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class ReverseDataBus extends BillingBaseDataBus {
  private AcctBookDto oldAcctBook;

  private PaymentDto oldPayment;

  private EventPaymentData oldEventPayment;

  private BillDto oldBill;

  private Long[] oldEventInstIdList;

  private AcctBookData[] reverseBalDeductAcctBookDataList;

  private AcctBookData[] reversePresentAcctBookDataList;

  private AcmInstData[] reverseAcmInstDataList;

  private BalDto reversedBal;

  private PaymentDto paymentDto;

//  private EventPaymentDto reverseEventPaymentDto;

  private PaymentSettDto reversePaymentSettDto;

  private PaymentSettItemDto[] reversePaymentSettItemDtoList;
}
