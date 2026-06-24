package com.sts.sinorita.svc.role.projection;

import java.time.LocalDateTime;

public interface UserHisProjection {

    Long getRecId();

    Long getUserId();

    String getUserName();

    String getUserCode();

    String getAddress();

    String getMemo();

    LocalDateTime getUserEffDate();

    LocalDateTime getUserExpDate();

    LocalDateTime getCreatedDate();

    String getState();

    LocalDateTime getStateDate();

    String getIsLocked();

    LocalDateTime getPwdExpDate();

    Integer getLoginFail();

    LocalDateTime getUnlockDate();

    Long getRecUserId();

    LocalDateTime getRecCreateDate();

    String getIpAddress();

    String getComments();

    String getRecUserName();
    String getRecUserCode();
}
