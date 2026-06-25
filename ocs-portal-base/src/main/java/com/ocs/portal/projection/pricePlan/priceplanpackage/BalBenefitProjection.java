package com.ocs.portal.projection.pricePlan.priceplanpackage;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BalBenefitProjection {
    Long getBalThresholdId();
    Long getSubBalTypeId();
    Double getValue();

    Long getAcctResId();
    Integer getPriority();
    Long getPeriodId();
    Double getCeilLimit();
    Double getFloorLimit();
    Double getDailyCeilLimit();
    Double getDailyFloorLimit();
    String getLimitSubs();
    String getPeriodRelUnit();
    Integer getAbsExpOffset();
    String getExtendRule();

    String getAcctResName();
    String getIsCurrency();
    String getComments();
    String getBalType();
    Double getCreditLimit();
    Integer getRemindDay();
    Double getRemindValue();
    Double getMaxValue();

    LocalDate getAbsEffDate();
    LocalDate getAbsExpDate();

    Integer getRelEffOffset();
    String getRelEffUnit();
    Integer getRelExpOffset();
    String getRelExpUnit();

    LocalTime getRelEffTime();
    LocalTime getRelExpTime();

    String getPeriodType();
    Integer getMaximumDays();
}
