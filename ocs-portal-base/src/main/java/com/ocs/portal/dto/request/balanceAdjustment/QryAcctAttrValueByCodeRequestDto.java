package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class QryAcctAttrValueByCodeRequestDto {

  private Long acctId;

  private String acctAttrCode;

  private String attrValue;

}
