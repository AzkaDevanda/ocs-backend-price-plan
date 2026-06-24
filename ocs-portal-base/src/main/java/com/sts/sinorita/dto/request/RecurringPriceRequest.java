package com.sts.sinorita.dto.request;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurringPriceRequest {
    private Integer priceVerId;

    //    @Schema(example = "3304")
//    @NotNull(message = "offerId cannot be null")
    private Integer offerVerId;

    @Nullable
    private Integer mappingId;

    @Schema(example = "-1")
    private Integer ratePlanId;

    @Schema(example = "2025-11-11")
    @NotNull(message = "effDate cannot be null")
    private LocalDate effDate;

    @Schema(example = "2025-11-11")
    private LocalDate expDate;

    @Size(min = 1, max = 255, message = "priceName must be between 1 and 255 characters")
    @Schema(example = "voucher testing 200")
    private String priceName;

    private String payIndicator;

    private Long creditLimit;

    @Schema(example = "1")
//    list dari acctitemtypename
    private Integer resultAccountItemType;

    private String remarks;

    @Schema(example = "1")
    private Integer roundMode;

    //    value = price
    @Schema(example = "10")
    private String price;

    @Schema(example = "1024")
//    calculate unit = rum
    private Long calculateUnit;

    @Schema(example = "C")
    private Character rpPriceUnit;

    @Schema(example = "A")
    private Character newConnection;

    @Schema(example = "A")
    private Character termination;

    @Schema(example = "A")
    private Character normal;

    @Schema(example = "N")
    private Character inAdvance;

    AdvanceBenefitRequestDto expressionPrice;

}
