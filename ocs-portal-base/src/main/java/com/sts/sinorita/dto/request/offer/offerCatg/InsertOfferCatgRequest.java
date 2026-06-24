package com.sts.sinorita.dto.request.offer.offerCatg;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InsertOfferCatgRequest {
    @NotNull(message = "categoryName cannot be null")
    private String categoryName;
    private String categoryCode;
    private String remarks;
    private Character offerType;
}
