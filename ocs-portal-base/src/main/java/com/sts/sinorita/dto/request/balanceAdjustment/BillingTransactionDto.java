package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillingTransactionDto {
  private Long subsEventId;
  private Long contactChannelId;
  private Long reasonId;
  private Long charge1;
  private Long glCoefficient;
  private LocalDateTime createdDate;
  private String glCode1;
  private String glDirection1;
  private LocalDateTime partnerDate;
  private String glCode;
  private String glDirection;
  private Long acctId;
  private Long eventInstId;
  private Long subsId;
  private Long acctResId;
  private Long acctBookId;
  private String acctBookType;
  private Long paymentMethodId;
  private Long preBalance1;
  private String BillingTransaction;
  private Long spId;
  private Long transactionId;
  private Long seq;
  private Long eventPaymentId;
  private List<Long> chargeList;

}
