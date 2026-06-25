package com.ocs.portal.dto.request.balanceAdjustment;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CdrDataDict {
  private Long subsId;
  private Long acctBookId;
  private Long charge;
  private Long channel;
  private String transaction;
  private LocalDateTime createdDate;
  private String prefix;
  private String accNbr;
  private String msisdn;
  private Long eventInstId;
  private Long subsEventId;
  private Long contactChannelId;
  private Long amount;


}
