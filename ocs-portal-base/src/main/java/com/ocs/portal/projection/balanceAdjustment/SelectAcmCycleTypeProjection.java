package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface SelectAcmCycleTypeProjection {
  Long getAcmCycleTypeId ();

  String getTimeUnit ();

  Long getQuantity ();

  Date getBeginDate ();

  String getRefType ();

  Long getReAttr ();

  Long getSpId ();
}
