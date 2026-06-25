package com.ocs.portal.svc.role.projection;

public interface RoleProjection {

    Long getRoleId();

    String getRoleName();

    String getComments();

    String getRoleCode();

    Character getIsLocked();

    Long getAppId();

    Long getSpId();

    java.time.LocalDate getCreatedDate();

    java.time.LocalDate getUpdateDate();

    String getOrderFields();
}
