package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTriggerBenefitDto {

    // ACM BENEFIT
    private Integer benefitValue;

    // SUB_BAL_TYPE
    private Integer accountBalanceType;
    private List<Integer> resultAccountItemType;
    private Long ceilLimit;
    private Long dailyCeilLimit;
    private Integer maximumDays;
    private Character subscriberOnly;
    private Character extendRule;
    private Long offsetOfAbsoluteExpiry;

    // PERIOD TYPE ABSOLUTE
    private LocalDate absoluteEffDate;
    private LocalDate absoluteExpDate;

    private String periodType;

    // PERIOD TYPE RELATIVE
    private Integer offsetOfEffectiveDate;
    private Character effUnit;
    private Integer durationOfAvailability;
    private Character expUnit;
    private LocalTime relativeEffTime;
    private LocalTime relativeExpTime;
    private Character relativePeriodUnit;

}
