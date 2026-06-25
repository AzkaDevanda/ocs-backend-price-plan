package com.ocs.portal.svc.role.projection;

import java.time.LocalDate;

public interface JobProjection {
    Long getJobId();
    String getJobName();
    String getState();
    LocalDate getStateDate();
    Long getSpId();
    String getJobCode();
    Long getParJobId();

}
