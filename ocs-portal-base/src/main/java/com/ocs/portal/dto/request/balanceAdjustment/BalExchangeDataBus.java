package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import lombok.Data;

@Data
public class BalExchangeDataBus extends BillingBaseDataBus {
  private BalTransferDto balTransferDto;

  private String acctBookTypeOut;

  private String acctBookTypeIn;

//  private BalExchangeRuleDto balExchangeRuleDto;

  private Long gainAmount;

  private AcctBookData acctBookDataOut;

  private AcctBookData acctBookDataIn;

  private RecordBalanceChangeParam recordBalanceChangeParamOut;

  private RecordBalanceChangeParam recordBalanceChangeParamIn;

  private AcctResDto acctResDtoOut;

  private AcctResDto acctResDtoIn;
}
