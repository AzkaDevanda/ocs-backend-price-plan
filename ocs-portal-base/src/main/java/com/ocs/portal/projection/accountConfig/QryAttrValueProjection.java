package com.ocs.portal.projection.accountConfig;

public interface QryAttrValueProjection {
    Integer getBaseAttrId();
    Integer getAttrValueId();
    String getValueMark();
    String getValue();
    Integer getParentAttrValueId();
    Integer getParentAttrId();
    String getAttrName();
}
