package com.ocs.portal.dto.response.attr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttrListByCatgResponseDto {
  private Integer attrId;
  private String attrName;
  private String attrType;
  private Integer objAttrId;
  private String attrCode;
  private String csrVisible;
  private String instantiatable;
  private String configVisible;
  private String editable;
}
