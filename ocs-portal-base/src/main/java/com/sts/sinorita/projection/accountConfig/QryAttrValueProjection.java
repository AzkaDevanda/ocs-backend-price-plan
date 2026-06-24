package com.sts.sinorita.projection.accountConfig;

public interface QryAttrValueProjection {
    Integer getBaseAttrId();
    Integer getAttrValueId();
    String getValueMark();
    String getValue();
    Integer getParentAttrValueId();
    Integer getParentAttrId();
    String getAttrName();
}
