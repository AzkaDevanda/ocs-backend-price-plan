package com.sts.sinorita.projection.balanceAdjustment;

public interface SelectOfferAttrListByAttrCodeProjection {
  Long getOfferId ();

  Long getAttrId ();

  String getDefaultValue ();

  String getAttrName ();

  String getAttrType ();

  String getAttrCode ();

  String getCsrVisible ();

  String getInstantiatable ();

  Long getSpId ();
}
