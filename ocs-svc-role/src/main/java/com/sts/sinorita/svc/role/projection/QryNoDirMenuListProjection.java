package com.sts.sinorita.svc.role.projection;

import java.time.LocalDateTime;

public interface QryNoDirMenuListProjection {
  Long getMenuId();

  String getMenuType();

  String getIconUrl();

  String getState();

  LocalDateTime getStateDate();

  Long getPrivId();

  String getPrivName();

  String getPrivCode();

  String getUrl();

  String getPrivEl();

  String getPrivType();

  String getMenuName();

  String getMenuUrl();

  String getComments();

  String getIsHold();

  Long getSpId();

  Long getAppId();

  String getCdnUrl();
}
