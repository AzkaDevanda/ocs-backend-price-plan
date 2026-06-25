package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class AttrDto {
  public Long attrId;

  public String attrName;

  public String attrType;

  public Long objAttrId;

  public String attrCode;

  public String csrVisible;

  public String instantiatable;

  public Long spId;

  public String configVisible;

  public String defaultValue;
}
