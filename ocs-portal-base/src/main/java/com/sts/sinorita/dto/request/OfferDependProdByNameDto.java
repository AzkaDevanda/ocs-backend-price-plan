package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDependProdByNameDto {
    private Integer offerId;
    private String offerName;
    private String offerCode;
    private Integer offerCatgId;
}
