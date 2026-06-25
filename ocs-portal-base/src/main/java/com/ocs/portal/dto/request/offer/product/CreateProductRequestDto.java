package com.ocs.portal.dto.request.offer.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {
    private Character offerType;
    private Long offerCatgId;
    private String productName;
    private LocalDate effDate;
    private LocalDate expDate;
    private String code;
    @Schema(defaultValue = "B")
    private String effType;
    private Integer serviceType;
    private Character paidFlag;
    private Integer lifecycleType;
    private Integer brandPricePlanId;
    private String remarks;
    @Schema(defaultValue = "0")
    private Integer spId;
}
