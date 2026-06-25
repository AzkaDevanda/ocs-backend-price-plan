package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface SelectCurBillingCycleIDByAcctIdProjection {
  Long getBillingCycleId ();

  Long getBillingCycleTypeId ();

  Date getCycleBeginDate ();

  Date getCycleEndDate ();

  String getState ();

  Date getDebtDate ();

  Long getSpId ();

  Date getRunDate ();
}
