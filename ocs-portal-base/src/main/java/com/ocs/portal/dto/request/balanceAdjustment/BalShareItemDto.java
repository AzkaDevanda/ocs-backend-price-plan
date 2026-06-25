package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class BalShareItemDto {
  public Long balShareId;

  public Long acctItemTypeId;

  public String acctItemTypeName;

  public Long spId;
}
