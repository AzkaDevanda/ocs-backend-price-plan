package com.ocs.portal.dto.request.balanceAdjustment.add;

import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class AddBalanceAccountRequestDto {
  private Long acctId;
  private String acctNbr;
  private Long acctResId;
  private Long balance;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
  private LocalDateTime effDate;
  
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
  private LocalDateTime expDate;
  private String comment;
  private String partyType;
  private String partyCode;
  private Long contactChannelId;
}