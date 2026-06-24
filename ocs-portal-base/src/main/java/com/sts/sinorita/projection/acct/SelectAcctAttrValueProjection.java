package com.sts.sinorita.projection.acct;

import java.time.LocalDateTime;

public interface SelectAcctAttrValueProjection {
  Long getAcctId();

  Long getAttrId();

  String getAttrValue();

  LocalDateTime getEffDate();

  LocalDateTime getExpDate();

  LocalDateTime getUpdateDate();

  String getNeedUpload();

  Long getSpId();
}
