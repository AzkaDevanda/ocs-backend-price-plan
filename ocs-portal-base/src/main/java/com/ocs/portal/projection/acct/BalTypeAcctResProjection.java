package com.ocs.portal.projection.acct;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BalTypeAcctResProjection {
    Integer getBalType();
    String getBalTypeName();
    Long getAcctResId();
    String getAcctResName();
    Character getIsCurrency();
    String getComments();
    BigDecimal getCreditLimit();
    BigDecimal getMaxValue();
    Character getRefillable();
    String getStdCode();
    Character getPaymentForce();
    Long getParentAcctResId();
    Character getIsFreeUnit();
    Integer getDefaultAcctItemTypeId();
    String getParentAcctResName();
    String getAcctItemTypeName();
    Integer getUnitTypeId();
    BigDecimal getUnitPrecision();
    Integer getRatioMoney();
    Integer getRatioPrecision();
    Integer getPriority();
    Character getExtendRule();
    String getExtendRuleName();
    BigDecimal getMaxAdjustValue();
    BigDecimal getMaxChgValue();
    Integer getMaxExpDate();
    Character getResetZero();
    BigDecimal getGracePeriod();
    BigDecimal getMaxRollover();
    String getResetZeroName();
}
