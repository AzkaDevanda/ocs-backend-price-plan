package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.SubsPricePlanDto;
import com.sts.sinorita.projection.pricePlan.SubsPricePlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPricePlanMapper {
    SubsPricePlanDto toDto(SubsPricePlanProjection projection);
}
