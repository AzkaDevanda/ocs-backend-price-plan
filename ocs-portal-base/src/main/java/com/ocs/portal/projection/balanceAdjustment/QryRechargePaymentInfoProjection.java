package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface QryRechargePaymentInfoProjection {
  String getTradeSn ();

  Date getTradeTime ();

  String getTradeMethod ();

  String getTradeType ();

  String getAccountCode ();

  String getVcPin ();

  Long getAmount ();

  Long getExtendDays ();
}
