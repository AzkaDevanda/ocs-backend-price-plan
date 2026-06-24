package com.sts.sinorita.mapper.pricePlan.priceplanpackage;

import com.sts.sinorita.dto.response.priceplan.BalanceTresholdResponseDto;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalanceTresholdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceTresholdMapper {
    BalanceTresholdResponseDto toDto(BalanceTresholdProjection projection);
}
