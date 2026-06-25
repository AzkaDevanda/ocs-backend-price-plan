package com.ocs.portal.projection.baseattr;

public interface AttrDetailProjection {
  Integer getBaseAttrId();

  Integer getInputType();

  String getNullable();

  String getComments();

  String getDefaultValue();

  String getValueScript();

  Integer getTextAttrId();

  String getDataType();

  String getMask();

  String getRuleScript();

  String getEditable();

  String getExceptionMessage();

  Integer getMinValue();

  Integer getMaxValue();
}
