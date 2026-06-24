package com.sts.sinorita.projection.balanceAdjustment;

public interface SelectAcctDepositBalByAcctIdProjection {
  Long getDepositItemId ();

  Long getAcctId ();

  Long getDepositTypeId ();

  Long getBal ();

  Long getReserveBal ();
}
