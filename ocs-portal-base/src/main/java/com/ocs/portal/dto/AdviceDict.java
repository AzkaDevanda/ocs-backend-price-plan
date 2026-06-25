package com.ocs.portal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdviceDict {

  private String prefix;

  private String accNbr;

  private Long subsId;

  private Long acctId;

  private String stateSet;

  private String prodSpecName;

  private String pricePlanName;

  private LocalDateTime pricePlanEffDate;

  private LocalDateTime pricePlanExpDate;

  private LocalDateTime prodSpecEffDate;

  private LocalDateTime prodSpecExpDate;

  private LocalDateTime balanceExpDate;

  private Long currentBalance;

  private LocalDateTime afterBalanceExpDate;

  private Long afterBalance;

  private Long spId;

  private String prodSpecCode;

}
