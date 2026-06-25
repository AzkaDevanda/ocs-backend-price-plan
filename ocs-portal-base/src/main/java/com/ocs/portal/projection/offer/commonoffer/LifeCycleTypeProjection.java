package com.ocs.portal.projection.offer.commonoffer;

import java.sql.Clob;

public interface LifeCycleTypeProjection {
  String getLifecycleType();

  String getLifecycleTypeName();

  String getComments();

  String getSpId();

  Clob getExtAttr();
}
