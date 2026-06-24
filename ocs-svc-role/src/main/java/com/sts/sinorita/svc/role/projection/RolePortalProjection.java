package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RolePortalProjection {
    Long getRoleId();
    Long getPortalId();
    String getState();
    LocalDate getStateDate();
    String getIsDefault();
    Long getSpId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdateDate();

    String getPortalName();
    String getIconUrl();
    String getUrl();
    String getRoleName();
}
