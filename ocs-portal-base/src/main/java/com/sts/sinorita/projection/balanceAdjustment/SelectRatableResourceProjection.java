package com.sts.sinorita.projection.balanceAdjustment;

public interface SelectRatableResourceProjection {
  Long getResourceId ();

  String getAcmType ();

  String getMask ();

  String getResourceName ();

  String getComments ();

  Long getSpId ();

  Long getUnitTypeId ();

  Long getUnitPrecision ();

  String getUnitTypeName ();

  Long getPrecisionValue ();

  String getRoundWay ();

  Long getBillingCycleTypeId ();
}
