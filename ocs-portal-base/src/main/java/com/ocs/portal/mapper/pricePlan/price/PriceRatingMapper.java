package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.PriceDetailResponse;
import com.ocs.portal.projection.pricePlan.price.PriceRatingProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRatingMapper {
    PriceDetailResponse toDto(PriceRatingProjection priceRatingProjection);
}
