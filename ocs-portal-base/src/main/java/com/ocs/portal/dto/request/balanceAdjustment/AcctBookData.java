package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcctBookData {
  private final List<ReCcInstBLData> reCcInstDataList = new ArrayList<>();
  public String refAttr;
  public Long orgId;
  private Long acctBookId;
  private Long acctId;
  private Long acctResId;
  private String acctBookType;
  private LocalDateTime createdDate;
  private Long preBalance;
  private LocalDateTime preExpDate;
  private Long charge;
  private Long billId;
  private String partyType;
  private String partyCode;
  private Long preSuttleBal;
  private Long seconds;
  private Long balId;
  private Long contactChannelId;
  private Long eventPaymentId;
  private String glCode;
  private Long spId;
  private Long partId;
  private EventPaymentData eventPaymentData;
  private BalDto modBal;
  private AcctBookReconcileDto reconcileDto;
  private LocalDateTime preEffDate;
  private Long effSeconds;
  private AcctBookDto benefitSecondsAcctBookDto;
}
