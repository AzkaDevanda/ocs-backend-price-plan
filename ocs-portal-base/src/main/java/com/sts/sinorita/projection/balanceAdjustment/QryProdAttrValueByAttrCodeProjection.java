package com.sts.sinorita.projection.balanceAdjustment;

import java.util.Date;

public interface QryProdAttrValueByAttrCodeProjection {
  Long getProdId ();

  Long getAttrId ();

  String getValue ();

  Date getEffDate ();

  Date getExpDate ();

  Date getUpdateDate ();

  String getNeedUpload ();

  Long getSpId ();

  String getUploadType ();
}
