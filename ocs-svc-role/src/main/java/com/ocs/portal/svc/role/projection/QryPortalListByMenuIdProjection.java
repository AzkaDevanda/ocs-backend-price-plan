package com.ocs.portal.svc.role.projection;

public interface QryPortalListByMenuIdProjection {
  String getPortalName();

  Long getPortalId();

  Long getSeq();

  Long getPartyId();

  Long getParentId();

  String getType();
}
