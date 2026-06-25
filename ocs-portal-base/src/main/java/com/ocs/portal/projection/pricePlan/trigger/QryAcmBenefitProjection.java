package com.ocs.portal.projection.pricePlan.trigger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface QryAcmBenefitProjection {

    Integer getAcmThresholdId();
    Integer getSubBalTypeId();
    Integer getValue();

    Integer getAcctResId();
    Integer getPriority();
    Integer getPeriodId();
    Integer getMaximumDays();

    BigDecimal getCeilLimit();
    BigDecimal getFloorLimit();
    BigDecimal getDailyCeilLimit();
    BigDecimal getDailyFloorLimit();

    Character getPeriodRelUnit();
    Long getAbsExpOffset();
    Character getExtendRule();
    Character getLimitSubs();

    String getAcctResName();
    Character getIsCurrency();
    String getComments();
    String getBalType();

    BigDecimal getCreditLimit();
    Integer getRemindDay();
    BigDecimal getRemindValue();
    BigDecimal getMaxValue();

    LocalDate getAbsEffDate();
    LocalDate getAbsExpDate();

    Integer getRelEffOffset();
    Character getRelEffUnit();
    Integer getRelExpOffset();
    Character getRelExpUnit();
    LocalTime getRelEffTime();
    LocalTime getRelExpTime();

    String getPeriodType();

    Integer getDayOffset();

}
