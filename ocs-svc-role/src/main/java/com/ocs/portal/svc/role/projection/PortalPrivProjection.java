package com.ocs.portal.svc.role.projection;

public interface PortalPrivProjection {
    Long getPortalId();
    Long getSeq();
    Long getPartyId();
    Long getPrivId();
    String getPrivName();
    Character getType();
    String getUrl();
    String getComments();
}
