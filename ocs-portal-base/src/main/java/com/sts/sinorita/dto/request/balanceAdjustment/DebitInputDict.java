package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class DebitInputDict {
  private AcctDto acctDto;

  private Long acctId;

  private Long acctBookId;

  private String partyType;

  private String partyCode;

  private String groupType;

  private Long overdueAmount;

}
