package com.ocs.portal.dto.request;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitRequest {

    @Schema(example = "1819", description = "ID dari rate plan")
    // rate plan
    private Integer ratePlanId;

    @Schema(example = "3502", description = "ID versi offer")
    // offer ver id
    private Integer offerVerId;

    @Nullable
    private Integer mappingId;

    @Schema(example = "5010", description = "ID versi harga")
    // price ver id
    @Nullable
    private Integer priceVerId;

    @NotNull(message = "effectiveDate cannot be null")
    @Schema(example = "2025-04-23", description = "Tanggal mulai efektif")

    private LocalDate effectiveDate;

    @Schema(example = "2025-04-23", description = "Tanggal mulai efektif")
    private LocalDate expiryDate;

    // benefit name
    @NotNull(message = "benefitName cannot be null")
    @Schema(example = "Benefit data", description = "Nama benefit")
    private String benefitName;

    // remarks
    @Schema(example = "null")
    private String remarks;


    // benefit value
    @NotNull(message = "benefitValue cannot be null")
    @Schema(example = "1000", description = "Nilai benefit")
    private String benefitValue;

    // account balance type
    @NotNull(message = "acctResId cannot be null")
    @Schema(example = "28", description = "ID akun resource")
    private Integer acctBalanceTypeId;

    // calculation unit kanan
    @NotNull(message = "reAttrId cannot be null")
    @Schema(example = "101", description = "ID attribute yang digunakan untuk rule engine")
    private Integer reAttrId;

    // calculation unit
    @NotNull(message = "calculationUnit cannot be null")
    @Schema(example = "1", description = "Satuan perhitungan benefit")
    private Integer calculationUnit;

    // SubBalType
    // Cycle Floor Limit
    @Schema(example = "null")
    private Long cycleFloorLimit;

    // Daily Floor Limit
    @Schema(example = "null")
    private Long dailyFloorLimit;

    // Cycle Ceil Limit
    @Schema(example = "null")
    private Long cycleCeilLimit;

    // Daily Ceil Limit
    @Schema(example = "null")
    private Long dailyCeilLimit;

    // Maximum Days
    @Schema(example = "null")
    private Integer maximumDays;

    // subscription only
    @Schema(example = "null")
    private Character subscriberOnly;

    // Period
    // Absolute Effective Date
    @Schema(example = "2025-04-23", description = "Tanggal mulai efektif")
    private LocalDate absoluteEffectiveDate;

    // Absolute Expiry Date
    @Schema(example = "null")
    private LocalDate absoluteExpiryDate;

    // Offset Effective Date
    @Schema(example = "0", description = "Offset dari tanggal efektif (dalam angka)")
    private Integer offsetOfEffectiveDate;

    // effective unit
    @Schema(example = "D", description = "Satuan dari tanggal efektif (misal: D = Days)")
    private Character offsetOfEffectiveDateUnit;

    // Duration of Availability
    @Schema(example = "0", description = "Durasi ketersediaan benefit")
    private Integer durationOfAvailability;

    // availability unit
    @Schema(example = "D", description = "Satuan durasi ketersediaan")
    private Character durationOfAvailabilityUnit;

    // Relative Effective Time
    private LocalTime relativeEffectiveTime;

    // Relative Expiry Time
    private LocalTime relativeExpiryTime;

    // Relative Period Unit
    private Character relativePeriodUnit;

    // Offset of Absolute Expiry Date
    @Schema(example = "0")
    private Long offsetOfAbsoluteExpiry;

    // Bal Flags
    private String balFlags;

    AdvanceBenefitRequestDto advanceBenefit;
}
