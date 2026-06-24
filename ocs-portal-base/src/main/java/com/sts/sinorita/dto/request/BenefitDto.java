package com.sts.sinorita.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BenefitDto {

    // Header section
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String benefitName;
    private String remarks;
    private String priceType; // "Advanced" atau "Price Mapping"

    // Benefit Value tab
    private long benefitValue;
    private String accountBalanceType; // contoh: "Bonus Data"
    private Integer cycleFloorLimit;
    private Integer dailyFloorLimit;
    private Integer maximumDays;
    private String onlyConsumedAccountItemType;

    private String periodType; // "Absolute Value" atau "Relative Period"
    private int offsetOfEffectiveDate;
    private int durationOfAvailability;
    private LocalTime relativeEffectiveTime;
    private String relativePeriodUnit;
    private String balFlags;

    private int calculationUnitPer;
    private String calculationUnitType; // contoh: "Occurrence"
    private Integer repeatCount;
    private Integer cycleCeilLimit;
    private Integer dailyCeilLimit;
    private String subscriberOnly;

    private String unit; // "Day"
    private LocalTime relativeExpiryTime;
    private Integer offsetOfAbsoluteExpiry;
    private boolean isNeedStatistic;

}

