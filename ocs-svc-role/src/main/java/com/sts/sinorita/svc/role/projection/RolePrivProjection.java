package com.sts.sinorita.svc.role.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RolePrivProjection {
    Long getRoleId();

    Long getPrivId();

    Long getSpId();

    Character getPrivLevel();

    String getIsAuthorized();

    String getAutoOpenMenu();

    LocalDate getCreatedDate();

    LocalDate getUpdateDate();
}
