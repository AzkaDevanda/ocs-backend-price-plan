package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class ExtAttrBalanceChangeTrigger {

  private Long subsId;

  private Long charge;

  private String isLoanFreeEff;

  private String isValidateAcctBookType;

  private Long spId;

  private Long subsEventId;

  private Long onceFee;

  private Boolean isNeedLifeCycleInfo;


}
