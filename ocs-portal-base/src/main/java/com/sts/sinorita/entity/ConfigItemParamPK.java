package com.sts.sinorita.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the CONFIG_ITEM_PARAM database table.
 * 
 */
@Embeddable
public class ConfigItemParamPK implements Serializable {
  // default serial version id, required for serializable classes.
  private static final long serialVersionUID = 1L;

  @Column(name = "CONFIG_ITEM_ID")
  private long configItemId;

  @Column(name = "PARAM_CODE")
  private String paramCode;

  public ConfigItemParamPK() {
  }

  public long getConfigItemId() {
    return this.configItemId;
  }

  public void setConfigItemId(long configItemId) {
    this.configItemId = configItemId;
  }

  public String getParamCode() {
    return this.paramCode;
  }

  public void setParamCode(String paramCode) {
    this.paramCode = paramCode;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ConfigItemParamPK)) {
      return false;
    }
    ConfigItemParamPK castOther = (ConfigItemParamPK) other;
    return (this.configItemId == castOther.configItemId)
        && this.paramCode.equals(castOther.paramCode);
  }

  public int hashCode() {
    final int prime = 31;
    int hash = 17;
    hash = hash * prime + ((int) (this.configItemId ^ (this.configItemId >>> 32)));
    hash = hash * prime + this.paramCode.hashCode();

    return hash;
  }
}