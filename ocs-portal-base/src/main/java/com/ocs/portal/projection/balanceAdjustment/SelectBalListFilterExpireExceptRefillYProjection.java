package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface SelectBalListFilterExpireExceptRefillYProjection {
  Long getAcctId ();

  Long getAcctResId ();

  Long getGrossBal ();

  Long getConsumeBal ();

  Long getRatingBal ();

  Long getBillingBal ();

  LocalDateTime getEffDate ();

  LocalDateTime getExpDate ();

  LocalDateTime getUpdateDate ();

  Long getCeilLimit ();

  Long getFloorLimit ();

  Long getDailyCeilLimit ();

  Long getDailyFloorLimit ();

  Integer getPriority ();

  Long getInitBal ();

  Long getBalId ();

  Long getBalUsed ();

  Long getSubsId ();
}
