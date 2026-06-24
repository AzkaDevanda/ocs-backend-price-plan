package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.List;

@Data
public class SettlementParam {
  private Long settleCharge;

  private Long[] selectedAcctItemIdList;

  private Boolean isSettlementExecutive;

  private AcctItemDto[] acctItemList;

  private PaymentSettDto paymentSett;

  private List<PaymentSettItemDto> paymentSettItemList;

  private AcctItemDto[] newAcctItemList;

  private List<AcctItemDto> updateAcctItemList;

  private List<AcctItemDto> fullSettledAcctItemList;

  private AcctItemDto partialSettledAcctItem;
}
