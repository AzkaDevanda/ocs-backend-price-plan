package com.ocs.portal.svc.role.projection;

import java.time.LocalDateTime;

public interface UserPortalProjection {
    Long getUserId();
    String getState();
    LocalDateTime getStateDate();
    Long getPortalId();
    String getPortalName();
    String getIconUrl();
    String getUrl();
}
