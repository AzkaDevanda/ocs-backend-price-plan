package com.ocs.portal.dto.request;

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
public class UpdateBenefitRequestDto {
    @NotNull(message = "benefitName cannot be null")
    @Schema(example = "Benefit data", description = "Nama benefit")
    private String benefitName;

    private String remarks;

    @NotNull(message = "benefitValue cannot be null")
    @Schema(example = "1000", description = "Nilai benefit")
    private String benefitValue;

    @NotNull(message = "acctResId cannot be null")
    @Schema(example = "28", description = "ID akun resource")
    private Integer acctBalanceTypeId;

    @NotNull(message = "reAttrId cannot be null")
    @Schema(example = "101", description = "ID attribute yang digunakan untuk rule engine")
    private Integer reAttrId;

    @NotNull(message = "calculationUnit cannot be null")
    @Schema(example = "1", description = "Satuan perhitungan benefit")
    private Integer calculationUnit;

    // SubBalType
    @Schema(example = "null")
    private Long cycleFloorLimit;

    @Schema(example = "null")
    private Long dailyFloorLimit;

    @Schema(example = "null")
    private Long cycleCeilLimit;

    @Schema(example = "null")
    private Long dailyCeilLimit;

    @Schema(example = "null")
    private Integer maximumDays;

    @Schema(example = "null")
    private Character subscriberOnly;

    // PERIOD
    @Schema(example = "2025-04-23", description = "Tanggal mulai efektif")
    private LocalDate absoluteEffectiveDate;

    @Schema(example = "null")
    private LocalDate absoluteExpiryDate;

    @Schema(example = "0", description = "Offset dari tanggal efektif (dalam angka)")
    private Integer offsetOfEffectiveDate;

    @Schema(example = "D", description = "Satuan dari tanggal efektif (misal: D = Days)")
    private Character offsetOfEffectiveDateUnit;

    @Schema(example = "0", description = "Durasi ketersediaan benefit")
    private Integer durationOfAvailability;

    @Schema(example = "D", description = "Satuan durasi ketersediaan")
    private Character durationOfAvailabilityUnit;

    private LocalTime relativeEffectiveTime;

    private LocalTime relativeExpiryTime;

    private Character relativePeriodUnit;

    @Schema(example = "null")
    private Long offsetOfAbsoluteExpiry;

    private String balFlags;

    AdvanceBenefitRequestDto advanceBenefit;

}
