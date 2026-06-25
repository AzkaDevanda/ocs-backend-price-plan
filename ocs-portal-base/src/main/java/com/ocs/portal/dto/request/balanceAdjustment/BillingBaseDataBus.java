package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class BillingBaseDataBus {
  private LocalDateTime dateNow;

  private String acctBookType;

  private Boolean isRepeat;

  private Boolean acctLockFlag;

  private BillingRequest request;

  private SubsDto subs;

  private AcctDto acct;

  private BillDto bill;

  private BalDto bal;

  private Partner partner;

  private AcctBookData acctBookData;

  private AcctResDto acctResDto;

  private SettlementParam settlementParam;

  private LoanRepayParam loanRepayParam;

  private RecordBalanceChangeParam recordBalanceChangeParam;

  private BalChangeTriggerParam balChangeTriggerParam;

  private Map<String, Object> extendDataMap;

  private AcctBookData[] acctBookDataList;

  private AcctResDto[] acctResDtoList;

  private BillDto[] billList4SettleList;

  private List<AcctDto> otherAcctList;

  private PaymentPlanDto[] paymentPlanDtoList;
}
