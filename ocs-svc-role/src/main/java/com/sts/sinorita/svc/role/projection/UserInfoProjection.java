package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface UserInfoProjection {
    Long getUserId();
    String getUserName();
    String getUserCode();
    String getPwd();
    String getAddress();
    String getMemo();
    LocalDate getUserEffDate();
    LocalDate getUserExpDate();
    LocalDate getCreatedDate();
    String getState();
    LocalDate getStateDate();
    String getIsLocked();
    LocalDate getPwdExpDate();
    Integer getLoginFail();
    Long getPortalId();
    LocalDate getUnlockDate();
    LocalDate getUpdateDate();
}
