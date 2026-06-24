package com.sts.sinorita.dto.response.balanceAdjustment;

import lombok.Data;

@Data
public class RatableResourceDto {

  public Long resourceId;

  public String acmType;

  public String mask;

  public String resourceName;

  public String comments;

  public Long reAttr;

  public Long spId;

  public Long offset;

  public Long timeUnit;

  public Long unitTypeId;

  public Long unitPrecision;

  public String unitTypeName;

  public Long precision;

  public String roundWay;

  public Long acctResId;

  public Long billingCycleTypeId;
}
