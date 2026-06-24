package com.sts.sinorita.svc.role.projection;

public interface ProdUserPrivProjection {
    Long getUserId();
    Long getPrivId();
    String getPrivLevel();
    String getPrivType();
    String getPrivName();
    String getComments();
    String getUrl();
    String getUserName();
}
