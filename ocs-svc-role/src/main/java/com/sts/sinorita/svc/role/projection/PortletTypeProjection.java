package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface PortletTypeProjection {
    Long getTypeId();
    String getTypeName();
    String getComments();
    String getState();
    LocalDate getStateDate();
}
