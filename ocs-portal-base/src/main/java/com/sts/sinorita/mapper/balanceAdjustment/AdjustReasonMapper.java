package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.balanceAdjustment.AdjustDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAdjustReasonProjection;

@Mapper(componentModel = "spring")
public interface AdjustReasonMapper {
  AdjustDto toAdjustDto(SelectAdjustReasonProjection projection);
}
