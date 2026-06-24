package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class QryAcmBenefitResponseDto {
    private Integer acmThresholdId;
    private Integer subBalTypeId;
    private Integer value;

    private Integer acctResId;
    private Integer priority;
    private Integer periodId;
    private Integer maximumDays;

    private BigDecimal ceilLimit;
    private BigDecimal floorLimit;
    private BigDecimal dailyCeilLimit;
    private BigDecimal dailyFloorLimit;

    private Character periodRelUnit;
    private Long absExpOffset;
    private Character extendRule;
    private Character limitSubs;

    private String acctResName;
    private Character isCurrency;
    private String comments;
    private String balType;

    private BigDecimal creditLimit;
    private Integer remindDay;
    private BigDecimal remindValue;
    private BigDecimal maxValue;

    private LocalDate absEffDate;
    private LocalDate absExpDate;

    private Integer relEffOffset;
    private Character relEffUnit;
    private Integer relExpOffset;
    private Character relExpUnit;
    private LocalTime relEffTime;
    private LocalTime relExpTime;

    private String periodType;

    private Integer dayOffset;

}
