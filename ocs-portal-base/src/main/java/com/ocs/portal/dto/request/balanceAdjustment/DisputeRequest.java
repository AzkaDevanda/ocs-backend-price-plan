package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class DisputeRequest extends BillingRequest {
  public Long[] acctItemIdList;
  public String comments;
  public Long disputeId;
  private Long processType;
}
