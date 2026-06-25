package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BalTransferReverseDataBus extends BillingBaseDataBus {
  private String acctBookType;

  private Boolean isRepeat;

  private Boolean acctLockFlag;

  private BillingRequest request;

  private SubsDto subs;

  private AcctDto acct;

  private AcctBookData[] reverseBalDeductAcctBookDataList;

//  private BillDto bill;

//  private BalDto bal;

  private Partner partner;

  private AcctBookData acctBookData;

  private AcctResDto acctResDto;

  private SettlementParam settlementParam;

  private AcmInstData[] reverseAcmInstDataList;

  private LoanRepayParam loanRepayParam;

  private AcctBookData[] reversePresentAcctBookDataList;

  private RecordBalanceChangeParam recordBalanceChangeParam;

  private BalChangeTriggerParam balChangeTriggerParam;

  private Map<String, Object> extendDataMap;

  private AcctBookData[] acctBookDataList;

  private AcctResDto[] acctResDtoList;

  private BillDto[] billList4SettleList;

  private List<AcctDto> otherAcctList;

  private PaymentPlanDto[] paymentPlanDtoList;

  private PaymentSettItemDto[] reversePaymentSettItemDtoList;

  private PaymentSettDto reversePaymentSettDto;
}
