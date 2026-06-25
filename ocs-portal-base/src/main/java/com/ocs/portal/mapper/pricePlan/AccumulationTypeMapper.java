package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.response.priceplan.AccumulationTypeResponseDto;
import com.ocs.portal.projection.pricePlan.AccumulationTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccumulationTypeMapper {
    AccumulationTypeResponseDto toDto(AccumulationTypeProjection projection);
}
