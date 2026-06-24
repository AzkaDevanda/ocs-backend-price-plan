package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.response.offerver.PricePlanVerByPricePlanIdResponseDto;
import com.sts.sinorita.projection.pricePlan.PricePlanVerByPricePlanIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricePlanVerByPricePlanIdMapper {
    PricePlanVerByPricePlanIdResponseDto toDto(PricePlanVerByPricePlanIdProjection projection);

}
