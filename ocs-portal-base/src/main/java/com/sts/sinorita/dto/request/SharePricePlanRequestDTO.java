package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharePricePlanRequestDTO {

    private Integer sharedPricePlanVerId;
    private Integer offerId;
    private String isCopyOfferAttr;
}
