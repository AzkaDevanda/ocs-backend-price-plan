package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class PostInvoicingAdjustRequest extends BillingRequest {
  public String comments;

  public Long[] acctItemTypeIdList;

  public Long[] acctItemChargeList;

  public Long[] refAcctItemIdList;

  public Long[] subsIdList;

  public Long adjustReasonId;

  public Long acctBookId;

  public Boolean[] withTaxList;

  public Long[] taxRateList;

  public Long[] refBillIdList;

  public String[] commentList;
}
