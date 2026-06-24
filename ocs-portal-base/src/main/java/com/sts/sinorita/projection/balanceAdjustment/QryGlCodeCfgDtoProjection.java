package com.sts.sinorita.projection.balanceAdjustment;

public interface QryGlCodeCfgDtoProjection {
  Integer getPriority ();

  Long getSubsEventId ();

  Integer getAcctBookType ();

  Long getPaymentMethodId ();

  Integer getLoanType ();

  Long getDepositTypeId ();

  Long getAcctResId ();

  Long getReasonId ();

  Long getAcctItemTypeId ();

  Integer getGlDirection ();

  Integer getGlCoefficient ();

  String getGlCode ();

  Long getSpId ();

  Integer getLedgerType ();
}
