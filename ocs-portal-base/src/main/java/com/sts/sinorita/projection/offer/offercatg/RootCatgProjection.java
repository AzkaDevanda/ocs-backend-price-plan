package com.sts.sinorita.projection.offer.offercatg;

import java.time.LocalDateTime;

public interface RootCatgProjection {
  Integer getOfferCatgId();

  String getOfferCatgName();

  Integer getSeq();

  String getOfferCatgCode();

  LocalDateTime getEffDate();

  LocalDateTime getExpDate();

  String getComments();

  Integer getOfferCatgType();

  String getOfferCatgClass();

  String getPolicyFlag();
}
