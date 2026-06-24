package com.sts.sinorita.dto.request.balanceAdjustment.adjust;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdjustBalanceRequest {
  private Long balId;
  private Long acctId;
  private Long acctResId;
  private String acctNbr;
  private String accNbr;
  private String partyType;
  private String partyCode;
  private Long subsId;
  private Integer adjustReason;
  private LocalDateTime effDate;
  private Long contactChannelId;
  private String comments;
  private String userName;
  private String charge;
  private Integer spId;
  private LocalDateTime expDate;
  private Integer expiryExtention;
  private String opType;
  private Boolean acctLockFlag;
}
