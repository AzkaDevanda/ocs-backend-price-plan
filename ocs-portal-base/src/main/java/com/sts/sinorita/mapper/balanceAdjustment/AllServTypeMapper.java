package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.balanceAdjustment.AllServTypeDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllServTypeProjection;

@Mapper(componentModel = "spring")
public interface AllServTypeMapper {

  AllServTypeDto toAllServTypeDto(SelectAllServTypeProjection projection);

}
