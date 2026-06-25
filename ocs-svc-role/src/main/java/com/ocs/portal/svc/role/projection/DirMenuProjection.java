package com.ocs.portal.svc.role.projection;

public interface DirMenuProjection {
  Long getMenuId();

  Long getDirId();

  String getPrivName();

  String getPrivCode();

  String getUrl();

  String getIconUrl();

  String getIsHold();

  String getIsAuthorized();
}