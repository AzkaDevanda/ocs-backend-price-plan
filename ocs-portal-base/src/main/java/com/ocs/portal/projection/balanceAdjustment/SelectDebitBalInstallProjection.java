package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface SelectDebitBalInstallProjection {
  Long getAcctId ();

  Long getBal ();

  Long getSpId ();

  Long getCommissionCharge ();

  Long getRetCharge ();

  Long getCommRetCharge ();

  String getLoanType ();

  Long getInstallSeq ();

  LocalDateTime getLastRetDate ();
}

