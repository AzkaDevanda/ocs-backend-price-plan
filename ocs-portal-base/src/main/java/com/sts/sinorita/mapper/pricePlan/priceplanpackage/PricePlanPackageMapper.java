package com.sts.sinorita.mapper.pricePlan.priceplanpackage;

import com.sts.sinorita.dto.response.priceplan.PricePlanJoinPackageResponseDto;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.PricePlanJoinPackageProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricePlanPackageMapper {
    PricePlanJoinPackageResponseDto toDto(PricePlanJoinPackageProjection projection);
}
