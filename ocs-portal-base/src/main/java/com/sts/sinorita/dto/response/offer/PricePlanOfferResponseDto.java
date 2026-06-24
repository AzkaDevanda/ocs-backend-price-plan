package com.sts.sinorita.dto.response.offer;

import com.sts.sinorita.entity.Offer;
import com.sts.sinorita.entity.OfferCatGMem;
import com.sts.sinorita.entity.PricePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanOfferResponseDto {
    private PricePlan pricePlan;
    private Object pricePlanDetail; // SubsPricePlan atau AcctPricePlan
    private Offer offer;
    private OfferCatGMem offerCatGMem;
}
