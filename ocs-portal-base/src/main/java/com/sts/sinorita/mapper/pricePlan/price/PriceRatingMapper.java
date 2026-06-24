package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.PriceDetailResponse;
import com.sts.sinorita.projection.pricePlan.price.PriceRatingProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRatingMapper {
    PriceDetailResponse toDto(PriceRatingProjection priceRatingProjection);
}
