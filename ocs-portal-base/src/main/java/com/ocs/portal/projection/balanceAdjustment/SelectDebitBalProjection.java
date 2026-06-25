package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface SelectDebitBalProjection {
  Long getAcctId ();

  Long getBal ();

  Long getSpId ();

  Long getCommissionCharge ();

  String getIsCommChargeNotRet ();

  Long getLastDebitCharge ();

  String getLoanType ();

  LocalDateTime getLastDebitDate ();

  Long getLastRetCharge ();

  LocalDateTime getLastRetDate ();

  Long getLastCommCharge ();

  Long getLastCommRetCharge ();

  Long getLastDebitBalId ();

  String getPricePlanCode ();

  String getLastButNDebitInfo ();
}
