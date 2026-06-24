package com.sts.sinorita.projection.trigger;

import java.time.LocalDate;

public interface NotifyParamsIdProjection {
    Integer getNotifyParamsId();
    String getNotifyName();
    Character getNotifyType();
    Integer getNotifyId();
    String getComments();
    LocalDate getEffDate();
    LocalDate getExpDate();
    Integer getSpId();
}
