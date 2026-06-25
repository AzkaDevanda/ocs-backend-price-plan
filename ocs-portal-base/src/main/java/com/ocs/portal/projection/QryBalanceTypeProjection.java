package com.ocs.portal.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface QryBalanceTypeProjection {
  String getBalType();

  String getBalTypeName();

  Long getAcctResId();

  String getAcctResName();

  String getIsCurrency();

  String getComments();

  BigDecimal getCreditLimit();

  BigDecimal getMaxValue();

  String getRefillable();

  String getStdCode();

  String getPaymentForce();

  Long getParentAcctResId();

  String getIsFreeUnit();

  Long getDefaultAcctItemTypeId();

  String getParentAcctResName();

  String getAcctItemTypeName();

  Long getUnitTypeId();

  Integer getUnitPrecision();

  String getUnitTypeName();

  BigDecimal getRatioMoney();

  Integer getRatioPrecision();

  Integer getPriority();

  String getExtendRule();

  String getExtendRuleName();

  BigDecimal getMaxAdjustValue();

  BigDecimal getMaxChgValue();

  Integer getMaxExpDate();

  String getResetZero();

  Integer getGracePeriod();

  Integer getMaxRollover();

  Integer getDayThreshold();

  Integer getWeekThreshold();

  Integer getMonthThreshold();

  Integer getDayCount();

  Integer getWeekCount();

  Integer getMonthCount();

  BigDecimal getMinResidualBal();

  BigDecimal getMaxAllowed();

  BigDecimal getMinAllowed();

  BigDecimal getTransferFactor();

  String getResetZeroName();
}
