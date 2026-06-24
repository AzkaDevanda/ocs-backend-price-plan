package com.sts.sinorita.projection.offer.offerapplyarea;

public interface OfferApplyAreaProjection {
  Integer getOfferId();

  Integer getAreaId();

  Integer getSpId();

  String getExcludeFlag();

  Integer getParentId();

  String getAreaName();

  String getConditionName();

  String getComments();

  String getAreaCode();
}
