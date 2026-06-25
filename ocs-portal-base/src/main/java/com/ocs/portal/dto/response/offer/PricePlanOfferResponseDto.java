package com.ocs.portal.dto.response.offer;

import com.ocs.portal.entity.Offer;
import com.ocs.portal.entity.OfferCatGMem;
import com.ocs.portal.entity.PricePlan;
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
