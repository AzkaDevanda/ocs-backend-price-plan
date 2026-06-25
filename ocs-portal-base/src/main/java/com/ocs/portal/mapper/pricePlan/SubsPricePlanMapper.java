package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.SubsPricePlanDto;
import com.ocs.portal.projection.pricePlan.SubsPricePlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPricePlanMapper {
    SubsPricePlanDto toDto(SubsPricePlanProjection projection);
}
