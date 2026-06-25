package com.ocs.portal.svc.role.projection;

import java.time.LocalDate;

public interface RolePrivMenuProjection {

    Long getPrivId();

    Character getPrivType();

    String getPrivName();

    String getComments();

    String getPrivLevel();

    String getUrl();

    String getIconUrl();
    Character getState();

    LocalDate getStateDate();

    String getPrivCode();

    String getPrivEl();

    String getAutoOpenMenu();
    Character getAddStatus();
    Character getEditStatus();
    Character getDeleteStatus();
    Character getReadStatus();
}
