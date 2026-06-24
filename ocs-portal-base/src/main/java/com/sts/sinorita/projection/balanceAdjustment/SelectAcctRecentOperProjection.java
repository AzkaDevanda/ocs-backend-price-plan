package com.sts.sinorita.projection.balanceAdjustment;

import java.util.Date;

public interface SelectAcctRecentOperProjection {
  Long getAcctId ();

  String getRecentOper ();

  Date getUpdateDate ();
}
