package com.sts.sinorita.mapper.attrvalue;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.attrvalue.AttrValueResponseDto;
import com.sts.sinorita.projection.attrvalue.AttrValueProjection;

@Mapper(componentModel = "spring")
public interface AttrValueMapper {
  AttrValueResponseDto toAttrValueResponseDto(AttrValueProjection projection);
}
