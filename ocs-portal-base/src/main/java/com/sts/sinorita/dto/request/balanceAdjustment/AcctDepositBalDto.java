package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class AcctDepositBalDto {
  private Long depositItemId;

  private Long acctId;

  private Long subsId;

  private Long depositTypeId;

  private Long bal;

  private Long reserveBal;

  private Date effDate;

  private Date expDate;

  private Date createdDate;

  private Long spId;

  private Long routingId;

  private String state;

  private Date stateDate;
}
