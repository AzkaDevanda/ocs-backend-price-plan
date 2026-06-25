package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface SelectAcctRecentOperProjection {
  Long getAcctId ();

  String getRecentOper ();

  Date getUpdateDate ();
}
