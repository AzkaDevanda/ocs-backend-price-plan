package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcctBookDto {
  public Long acctBookId;

  public Long acctId;

  public Long acctResId;

  public String acctBookType;

  public LocalDateTime createdDate;

  public Long preBalance;

  public LocalDateTime preExpDate;

  public Long charge;

  public Long billId;

  public String partyType;

  public String partyCode;

  public Long preSuttleBal;

  public Long seconds;

  public Long balId;

  public Long contactChannelId;

  public Long eventPaymentId;

  public String glCode;

  public Long spId;

  public Long partId;

  public LocalDateTime preEffDate;

  public Long effSeconds;

  public String refAttr;

  public Long eventInstId;
}
