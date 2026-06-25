package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.response.offerver.PricePlanVerByPricePlanIdResponseDto;
import com.ocs.portal.projection.pricePlan.PricePlanVerByPricePlanIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricePlanVerByPricePlanIdMapper {
    PricePlanVerByPricePlanIdResponseDto toDto(PricePlanVerByPricePlanIdProjection projection);

}
