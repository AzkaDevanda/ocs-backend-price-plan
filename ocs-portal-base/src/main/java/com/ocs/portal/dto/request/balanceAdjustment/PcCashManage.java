package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PcCashManage {
  private Long cashOperType;

  private Long amount;

  private Long bankId;

  private String checkNbr;

  private String counterNo;

  private String orderId;

  private Long eventPaymentId;

  private String remarks;

  private String category;

  private Long paymentMethodId;

  private LocalDateTime tradeTime;

  private Long currencyTypeId;

  private String partyType;

  private String partyCode;

  private Long orgId;

  private Long subsId;

  private String checkExpDate;

  private Long posId;

  private String offerIds;

  private String offerNames;
}
