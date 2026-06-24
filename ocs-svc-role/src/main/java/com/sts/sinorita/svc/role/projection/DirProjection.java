package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface DirProjection {
    Long getDirId();
    String getDirName();
    String getDirCode();
    Long getParentId();
    String getIconUrl();
    String getState();
    LocalDate getStateDate();
}
