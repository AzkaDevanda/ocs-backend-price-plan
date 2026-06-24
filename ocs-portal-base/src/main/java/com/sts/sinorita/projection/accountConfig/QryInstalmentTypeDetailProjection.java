package com.sts.sinorita.projection.accountConfig;

import java.time.LocalDate;

public interface QryInstalmentTypeDetailProjection {
  Long getId();

  String getInstalmentTypeName();

  Integer getFirstPay();

  Character getState();

  LocalDate getStateDate();

  String getComments();

  Integer getSpId();

  Integer getFeePercent();
}
