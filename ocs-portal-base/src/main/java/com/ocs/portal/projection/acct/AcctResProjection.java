package com.ocs.portal.projection.acct;

import java.math.BigDecimal;

public interface AcctResProjection {

    Long getAcctResId();

    Integer getBalType();

    Long getParentAcctResId();

    String getAcctResName();

    Character getIsCurrency();

    String getComments();

    BigDecimal getCreditLimit();

    Integer getRemindDay();

    BigDecimal getRemindValue();

    Long getMaxValue();

    Character getRefillable();

    Character getPaymentForce();

    String getStdCode();

    Character getIsFreeUnit();

    Integer getDefaultAcctItemTypeId();

    Integer getUnitTypeId();

    Integer getSpId();

    BigDecimal getUnitPrecision();

    Integer getRatioMoney();

    Integer getRatioPrecision();

    Integer getPriority();

    String getExtendRule();

    Long getMaxAdjustValue();

    Long getMaxExpDate();

    Long getMaxChgValue();

    Character getResetZero();

    String getPeriodClass();

    Integer getStoreUnit();

    BigDecimal getAcmThreshold();

    String getAcmType();

    Character getAcmUnit();

    Integer getAcmAmount();

    Long getCeilLimit();

    Long getFloorLimit();

    Long getDailyCeilLimit();

    Long getDailyFloorLimit();

    Character getOverdraftFlag();

    Character getBalanceAggregation();

    Character getRolloverFlag();

    String getCategory();

    Integer getReservePercentage();

    Character getBalCategory();

    Long getGracePeriod();

    Long getMaxRollover();

    Integer getUsageType();

    Character getRewardFlag();

    Character getUnlimitedFlag();

    Integer getAdjustType();

    String getFreeFlag();

    Character getAdjustFlag();

    Character getClearFlag();

    Integer getClearDays();

    Character getCustomerFlag();
}
