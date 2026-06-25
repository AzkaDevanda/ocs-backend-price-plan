package com.ocs.portal.svc.role.projection;

import java.time.LocalDate;

public interface UserRoleProjection {
    Long getUserId();
    Long getRoleId();
    Long getSpId();
    Long getId();
    Integer getUserRoleTimes();
    Integer getStaffRoleTimes();
    LocalDate getCreatedDate();
    LocalDate getUpdateDate();
}
