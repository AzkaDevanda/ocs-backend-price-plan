package com.sts.sinorita.projection.balanceAdjustment;

import java.time.LocalDateTime;
import java.util.Date;

public interface SelectAllBalListByAcctIdExtRawProjection {
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

  Long getSubsId ();

  Long getDailyCeilLimit ();

  Long getDailyFloorLimit ();

  Long getPriority ();

  Long getInitBal ();

  Long getBalId ();

  Long getVarCeilLimit ();

  Long getAbsExpOffset ();
}
