package com.ocs.portal.projection.attrvalue;

public interface AttrValueProjection {
  Integer getBaseAttrId();

  Integer getAttrValueId();

  String getValueMark();

  String getValue();

  Integer getParentAttrValueId();

  Integer getParentAttrId();

  String getAttrName();

  String getAttrCode();
}
