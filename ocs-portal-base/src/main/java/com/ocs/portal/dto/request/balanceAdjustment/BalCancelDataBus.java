package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalCancelDataBus extends BillingBaseDataBus {
  private Boolean isValidateNotPassed;

  private Boolean isPreValidateNotPassed;
  private AcctBookData[] balCancelAcctBookDataList;
}
