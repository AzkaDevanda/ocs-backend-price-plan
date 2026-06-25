package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.balanceAdjustment.AdjustDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAdjustReasonProjection;

@Mapper(componentModel = "spring")
public interface AdjustReasonMapper {
  AdjustDto toAdjustDto(SelectAdjustReasonProjection projection);
}
