package com.sts.sinorita.svc.role.projection;

import java.time.LocalDateTime;

public interface UserRoleExtProjection {
    Long getUserId();
    Long getRoleId();
    String getRoleName();
    String getComments();
    String getRoleCode();
    String getIsLocked();
    Integer getUserRoleTimes();
    Integer getStaffRoleTimes();
    Long getSpId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdateDate();
}
