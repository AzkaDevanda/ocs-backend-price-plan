package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class AcctServPriceLimitDto {
  public String acctBookType;

  public Long pricePlanId;

  public String suitableType;

  public Long spId;
}
