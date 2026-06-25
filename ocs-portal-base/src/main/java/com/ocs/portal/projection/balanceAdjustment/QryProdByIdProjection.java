package com.ocs.portal.projection.balanceAdjustment;

import java.util.Date;

public interface QryProdByIdProjection {
  Long getProdId ();

  Long getOfferId ();

  Date getCompletedDate ();

  String getProdState ();

  Long getSubsPlanId ();

  String getPackageFlag ();

  Long getParentProdId ();

  Long getIndepProdId ();

  Date getProdStateDate ();

  Date getUpdateDate ();

  Date getCreatedDate ();

  String getState ();

  Date getStateDate ();

  String getBlockReason ();

  Date getProdExpDate ();

  String getNeedUpload ();

  Date getAgreementExpDate ();

  Long getSpId ();

  Date getAgreementEffDate ();

  Long getAgreementLimit ();

  String getAgreementTimeUnit ();

  Long getRoutingId ();

  Date getActiveDate ();

  String getUploadType ();
}
