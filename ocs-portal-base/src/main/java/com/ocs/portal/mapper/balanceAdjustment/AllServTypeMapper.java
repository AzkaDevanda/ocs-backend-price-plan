package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.balanceAdjustment.AllServTypeDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAllServTypeProjection;

@Mapper(componentModel = "spring")
public interface AllServTypeMapper {

  AllServTypeDto toAllServTypeDto(SelectAllServTypeProjection projection);

}
