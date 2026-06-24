package com.sts.sinorita.svc.role.projection;

import java.util.Date;

public interface QryMenuListByDirIdProjection {
  Long getMenuId();

  String getIconUrl();

  String getState();

  Date getStateDate();

  String getMenuName();

  String getMenuUrl();

  String getComments();

  String getIsHold();

  Long getSpId();

  Long getAppId();
}
