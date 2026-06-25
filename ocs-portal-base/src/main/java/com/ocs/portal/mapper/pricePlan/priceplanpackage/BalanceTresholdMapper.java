package com.ocs.portal.mapper.pricePlan.priceplanpackage;

import com.ocs.portal.dto.response.priceplan.BalanceTresholdResponseDto;
import com.ocs.portal.projection.pricePlan.priceplanpackage.BalanceTresholdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceTresholdMapper {
    BalanceTresholdResponseDto toDto(BalanceTresholdProjection projection);
}
