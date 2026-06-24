package com.sts.sinorita.mapper.offer;

import java.util.Date;

public interface QryAllPricePlanSpecProjection {
  Long getPricePlanId ();

  String getApplyLevel ();

  Integer getPriority ();

  String getPricePlanName ();

  String getOfferCode ();

  Long getSaleListPrice ();

  Long getRentListPrice ();

  Date getEffDate ();

  Date getExpDate ();

  Date getCreatedDate ();

  String getState ();

  Date getStateDate ();

  String getEffType ();

  String getAutoContinueFlag ();

  Integer getCycleQuantity ();

  String getTimeUnit ();

  String getDuplicateFlag ();

  String getComments ();
}
