package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface JobRoleProjection {
    Long getJobRoleId();
    Long getJobId();
    Long getRoleId();
    String getState();
    LocalDate getStateDate();
    Long getSpId();
    LocalDate getCreatedDate();
    LocalDate getUpdateDate();
}
