package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TriggerBenefitDto {


    private Double benefitValue;
    private Integer accountBalanceType;
    private Double cycleCeilLimit;
    private Double dailyCeilLimit;
    private Integer maximumDays;
    private Character subscriberOnly;
    private String extendRule;
    private List<Integer> resultAccountItemType;

    // Period Type: "Absolute" atau "Relative"
    private String periodType;

    // Relative Period Fields
    private LocalDate absoluteEffectiveDate;
    private LocalDate absoluteExpiryDate;
    private Integer offsetOfEffectiveDate;
    private Integer dayOffset;
    private String effUnit; // misalnya: "Day", "Hour" (D,H)
    private String expUnit; // misalnya: "Day", "Hour" (D,H)
    private Integer durationOfAvailability;
    private String relativeEffectiveTime; // format hh:mm:ss
    private String relativeExpiryTime;    // format hh:mm:ss
    @Schema(description = "relativePeriodUnit example D/H/M", example = "D")
    private String relativePeriodUnit;
    private Integer offsetOfAbsoluteExpiry;


    private Integer thresholdId;

}
