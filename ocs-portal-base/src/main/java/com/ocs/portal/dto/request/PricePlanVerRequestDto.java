package com.ocs.portal.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanVerRequestDto {

    @Nullable
    private Integer priceVerId;

    @Nullable
    private Integer mappingId;

    @Schema(example = "3304")
    @NotNull(message = "offerId cannot be null")
    private Integer offerVerId;

    @Schema(example = "-1")
    private Integer ratePlanId;

    @Schema(example = "2025-11-11")
    @NotNull(message = "effDate cannot be null")
    private LocalDate effDate;

    @Schema(example = "2025-11-11")
    private LocalDate expDate;

    @Schema(example = "1301")
    private Integer reId;

    @NotNull(message = "priceName cannot be null")
    @Size(min = 1, max = 255, message = "priceName must be between 1 and 255 characters")
    @Schema(example = "voucher testing 200")
    private String priceName;

    @Schema(example = "1")
//    list dari acctitemtypename
    private Integer acctItemTypeId;

    private Long creditLimit;

    @NotNull(message = "Price cannot be null")
//    value = refvalue id
    @Schema(example = "10")
    private String price;

    @Size(min = 1, max = 30, message = "payIndicat or must be between 1 and 30 characters")
    @Schema(example = "0000")
    private String payIndicator;

    //    @NotNull(message = "Calculate Unit cannot be null")
    @Schema(example = "1024")
//    calculate unit = rum
    private Long rum;

    //    list dari reAttrName
    private Integer reAttr;

//    private Long creditLimit;

    private String comments;

//    List<TimeSpanUpRequest> timeSpanUp;
//
//    List<RankDto> rankUp;
//
//    List<AcmUpDto> accumulationPrice;
//
//    List<AcmCalcDto> accumulationCalculation;

//    ExpressionPriceRequest expressionPrice;
}
