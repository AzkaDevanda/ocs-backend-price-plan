package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface SelectProdBySubsIdProjection {
  Long getProdId();

  String getOfferCode();

  Long getOfferId();

  LocalDateTime getCompletedDate();

  String getProdState();

  Long getSubsPlanId();

  String getSubsPlanCode();

  String getPackageFlag();

  Long getParentProdId();

  Long getIndepProdId();

  LocalDateTime getProdStateDate();

  LocalDateTime getUpdateDate();

  LocalDateTime getCreatedDate();

  String getState();

  LocalDateTime getStateDate();

  String getBlockReason();

  LocalDateTime getProdExpDate();

  String getNeedUpload();

  LocalDateTime getAgreementExpDate();

  Long getSpId();

  LocalDateTime getActiveDate();
}
