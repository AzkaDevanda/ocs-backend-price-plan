package com.sts.sinorita.svc.role.projection;

import java.time.LocalDate;

public interface PortalChannelProjection {

    Long getPortalId();

    String getPortalName();

    String getIconUrl();

    String getUrl();

    Character getState();

    LocalDate getStateDate();

    String getExtraUrl();

    Long getContactChannelId();

    Character getAllowExternalAccess();
}
