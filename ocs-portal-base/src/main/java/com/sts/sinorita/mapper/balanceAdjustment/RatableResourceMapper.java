package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.response.balanceAdjustment.RatableResourceDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectRatableResourceProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatableResourceMapper {

  RatableResourceDto toRatableResourceDto (SelectRatableResourceProjection projection);
}
