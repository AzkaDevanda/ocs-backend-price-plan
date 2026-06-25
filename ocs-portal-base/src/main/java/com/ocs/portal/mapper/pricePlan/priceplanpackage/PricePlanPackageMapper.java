package com.ocs.portal.mapper.pricePlan.priceplanpackage;

import com.ocs.portal.dto.response.priceplan.PricePlanJoinPackageResponseDto;
import com.ocs.portal.projection.pricePlan.priceplanpackage.PricePlanJoinPackageProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricePlanPackageMapper {
    PricePlanJoinPackageResponseDto toDto(PricePlanJoinPackageProjection projection);
}
