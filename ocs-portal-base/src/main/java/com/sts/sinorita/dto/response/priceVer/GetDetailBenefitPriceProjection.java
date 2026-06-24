package com.sts.sinorita.dto.response.priceVer;

import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;
import org.springframework.cglib.core.Local;

import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public interface GetDetailBenefitPriceProjection {
    Integer getPriceId();

    LocalDate getEffectiveDate();

    LocalDate getExpiryDate();

    String getBenefitName();

    String getRemarks();

    Character getConfigType();

    String getBenefitValue();

    Integer getAcctBalanceTypeId();

    String getAcctBalanceTypeName();

    Integer getCalculationUnit();

    Integer getReAttr();

    String getReAttrName();

    Integer getCycleFloorLimit();

    Integer getCycleCeilLimit();

    Integer getDailyFloorLimit();

    Integer getDailyCeilLimit();

    String getBalFlags();

    Integer getMaximumDays();

    Character getSubscriberOnly();

    LocalDate getAbsoluteEffectiveDate();

    LocalDate getAbsoluteExpiryDate();

    Integer getOffsetOfEffectiveDate();

    Character getOffsetOfEffectiveDateUnit();

    Integer getDurationOfAvailability();

    Character getDurationOfAvailabilityUnit();

    LocalTime getRelativeEffectiveTime();

    LocalTime getRelativeExpiryTime();

    Character getRelativePeriodUnit();

    Integer getOffsetOfAbsoluteExpiry();

    Clob getRule();

    String getRuleRemarks();

    Blob getScriptPage();

    Integer getScriptTempletId();

}
