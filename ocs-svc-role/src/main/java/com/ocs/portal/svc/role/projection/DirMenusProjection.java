package com.ocs.portal.svc.role.projection;

public interface DirMenusProjection {
    Long getPortalId();

    String getType();

    Long getPartyId();

    String getPartyName();

    String getUrl();

    Long getParentId();

    Long getSeq();

    String getIsHold();
}
