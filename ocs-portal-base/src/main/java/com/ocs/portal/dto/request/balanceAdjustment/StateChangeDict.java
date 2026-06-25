package com.ocs.portal.dto.request.balanceAdjustment;

import java.util.List;

import lombok.Data;

@Data
public class StateChangeDict {

  private BalDto[] curBalList;

  private Long subsId;

  private Long spId;

  private String partyType;

  private String partyCode;

  private String contactChannelId;

  private List<Long> subsIdList;

  private Long subsEventId;
  
  private BalCreditOrderDto[] balCreditOrderList;

}
