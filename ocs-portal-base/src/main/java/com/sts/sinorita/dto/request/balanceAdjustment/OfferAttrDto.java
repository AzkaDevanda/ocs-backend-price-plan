package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class OfferAttrDto {
  private Long offerId;

  private Long attrId;

  private String defaultValue;

  private Long spId;

//  private Attr attr;
}
