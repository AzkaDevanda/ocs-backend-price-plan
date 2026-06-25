package com.ocs.portal.projection.accountConfig;

public interface QryAttrDetailProjection {
    Integer getBaseAttrId();
    String getInputType();
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
    String getMinValue();
    String getMaxValue();
}
