package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.response.priceplan.AccumulationTypeResponseDto;
import com.sts.sinorita.projection.pricePlan.AccumulationTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccumulationTypeMapper {
    AccumulationTypeResponseDto toDto(AccumulationTypeProjection projection);
}
