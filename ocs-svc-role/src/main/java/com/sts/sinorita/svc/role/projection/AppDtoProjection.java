package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface AppDtoProjection {
    Long getAppId();
    String getAppName();
    String getAppCode();
    String getAppUrl();
    String getIconPath();
    String getComments();
    Long getSpId();
    String getState();
    LocalDate getStateDate();
}
