package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
public class GetDetailBenefitResponseDto {
    private Integer priceId;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String benefitName;
    private String remarks;
    private Character configType;
    private String benefitValue;
    private Integer acctBalanceTypeId;
    private String accountBalanceTypeName;
    private Integer calculationUnit;
    private Integer reAttr;
    private String reAttrName;
    private Integer cycleFloorLimit;
    private Integer cycleCeilLimit;
    private Integer dailyFloorLimit;
    private Integer dailyCeilLimit;
    private Integer maximumDays;
    private Character subscriberOnly;
    private LocalDate absoluteEffectiveDate;
    private LocalDate absoluteExpiryDate;
    private Integer offsetOfEffectiveDate;
    private Character offsetOfEffectiveDateUnit;
    private Integer durationOfAvailability;
    private Character durationOfAvailabilityUnit;
    private LocalTime relativeEffectiveTime;
    private LocalTime relativeExpiryTime;
    private Character relativePeriodUnit;
    private Integer offsetOfAbsoluteExpiry;
    private String balFlags;
    private String rule;
    private String ruleRemarks;
    private String scriptPage;
    private Integer scriptTempletId;
}
