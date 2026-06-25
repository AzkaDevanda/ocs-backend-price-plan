package com.ocs.portal.projection.offer;

import java.time.LocalDateTime;

public interface OfferApplyCatgProjection {
  Integer getOfferId();

  Integer getCatgId();

  Integer getSpId();

  Character getExcludeFlag();

  String getCatgType();

  String getCatgDefType();

  String getCatgName();

  String getConditionName();

  String getComments();

  LocalDateTime getCreatedDate();
}
