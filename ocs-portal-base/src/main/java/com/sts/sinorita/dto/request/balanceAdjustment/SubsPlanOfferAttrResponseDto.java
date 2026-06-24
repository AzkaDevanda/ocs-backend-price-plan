package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class SubsPlanOfferAttrResponseDto {
  private Long subsPlanOfferAttrId;
  private Long offerVerId;
  private Long offerId;
  private Long attrId;
  private String defaultValue;
  private Long spId;
}
