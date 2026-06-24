package com.sts.sinorita.dto.request.balanceAdjustment;

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
public class AcctBookReconcileDto {
  public Long acctBookId;

  public String reconcileState;

  public Long logId;

  public Long contactChannelId;

  public String partnerSn;

  public Long spId;

  public Long partId;

  public String acctBookType;

  public Long charge;

  public LocalDateTime createdDate;

  public LocalDateTime paymentDate;

  public String partnerDetail;

  public String transactionType;
}
