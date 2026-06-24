package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UsersRoleProjection {

    Long getUserId();
    String getUserName();
    String getUserCode();
//    String getPwd();
    String getAddress();
    String getMemo();
    LocalDateTime getUserEffDate();
    LocalDateTime getUserExpDate();
    LocalDate getCreatedDate();
    Character getState();
    LocalDate getStateDate();
    Character getIsLocked();
    LocalDate getPwdExpDate();
    Integer getLoginFail();
    Long getPortalId();
    LocalDate getUnlockDate();
    Long getRoleId();
    LocalDate getUpdateDate();

    String getRoleName();
    String getComments();
    String getRoleCode();
     Integer getUserRoleTimes();
    Integer getStaffRoleTimes();
    Long getSpId();
    String getRoleNameList();
    String getEmail();
    String getPhone();
}
