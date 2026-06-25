package com.ocs.portal.dto.response.priceplan;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PriceRatingDetailDto {

    private LocalDate effDate;

    private LocalDate expDate;

    private String priceName;

    private Integer acctItemTypeId;

    private String price;

    private Long rum;

    private Integer reAttr;

    private String remarks;

}
