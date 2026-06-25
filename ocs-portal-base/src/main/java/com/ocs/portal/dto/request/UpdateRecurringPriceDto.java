package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecurringPriceDto {

    @NotNull(message = "priceName cannot be null")
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
