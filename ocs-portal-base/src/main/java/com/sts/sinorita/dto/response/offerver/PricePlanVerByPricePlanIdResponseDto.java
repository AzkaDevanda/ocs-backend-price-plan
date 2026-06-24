package com.sts.sinorita.dto.response.offerver;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PricePlanVerByPricePlanIdResponseDto {
    private Integer offerVerId;
    private Integer offerId;
    private LocalDate effDate;
    private LocalDate expDate;
    private Integer seq;
    private Character state;
    private Integer refOfferVerId;
}
