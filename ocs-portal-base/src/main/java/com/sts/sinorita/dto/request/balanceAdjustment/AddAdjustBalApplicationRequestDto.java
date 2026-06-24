package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class AddAdjustBalApplicationRequestDto {
  private String acctNbr;
  private String isSubsLimit;
  private Long acctResId;
  private String balance;
  private String balUnit;
  private Date effDate;
  private Date expDate;
  private String comments;
  private Long grossBal;
  private Long contactChannel;
  private String routingId;
  private Long acctId;
  private String partyType;
  private String partyCode;
  private Long contactChannelId;
  private Long spId;
}
