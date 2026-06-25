package com.ocs.portal.dto.response.offeattr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferAttrResponseDto {
  private Integer attrId;
  private String attrName;
  private Integer attrType;
  private String attrCode;
  private String objAttrId;
  private String csrVisible;
  private String instantiatable;
  private String configVisible;
  private Integer offerId;
  private Integer dispOrder;
  private String defaultValue;
  private String attrValue;
  private String operationTypes;
}
