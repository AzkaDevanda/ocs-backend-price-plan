package com.ocs.portal.projection.balanceAdjustment;

import java.time.LocalDateTime;

public interface FindProdBySubsIdProjection {
  Long getProdId();

  Long getOfferId();

  LocalDateTime getCompletedDate();

  String getProdState();

  Long getSubsPlanId();

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

  String getOfferCode();

  LocalDateTime getActiveDate();

  String getSubsPlanCode();

}
