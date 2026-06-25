package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.request.priceplan.PriceInfoDto;
import com.ocs.portal.projection.pricePlan.price.PriceInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceInfoDto toDto(PriceInfoProjection priceInfoProjection);
}
