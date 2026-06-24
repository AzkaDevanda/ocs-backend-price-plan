package com.sts.sinorita.projection.offer.offerattr;

public interface SelectOfferAttrByOfferIdProjection {
  Long getOfferId();

  Long getAttrId();

  String getAttrCode();

  String getDefaultValue();

  Integer getDispOrder();

  Long getSpId();

  String getOperationTypes();

}
