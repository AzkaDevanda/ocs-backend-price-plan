package com.sts.sinorita.projection.accountConfig;

public interface QryAcctResListProjection {
    Integer getAcctResId();
    Integer getParentAcctResId();
    String getAcctResName();
    String getStdCode();
    String getIsCurrency();
    String getBalType();
    Integer getCreditLimit();
    String getComments();
    String getRefillable();
}
