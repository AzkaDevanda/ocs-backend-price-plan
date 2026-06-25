package com.ocs.portal.projection.trigger;

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
