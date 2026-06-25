package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.RatableResourceDto;
import com.ocs.portal.projection.balanceAdjustment.SelectRatableResourceProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatableResourceMapper {

  RatableResourceDto toRatableResourceDto (SelectRatableResourceProjection projection);
}
