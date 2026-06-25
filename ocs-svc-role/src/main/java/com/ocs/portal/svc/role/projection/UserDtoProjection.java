package com.ocs.portal.svc.role.projection;

import java.time.LocalDateTime;

public interface UserDtoProjection {
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
    String getPhone();
    String getEmail();
    String getIsLocked();
    LocalDateTime getPwdExpDate();
    Integer getLoginFail();
    LocalDateTime getUnlockDate();
    String getOpenId();
    String getUserType();
    String getAlias();
    LocalDateTime getLastLoginDate();
    Long getSecurityQuestionId();
    String getSecurityAnswer();
    String getThumbnailUri();
    String getExtAttr();
    String getPortalName();
    Long getPortalId();
    LocalDateTime getUpdateDate();
    String getUnit();
}
