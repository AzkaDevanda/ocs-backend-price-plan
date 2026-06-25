package com.ocs.portal.mapper.attrvalue;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.attrvalue.AttrValueResponseDto;
import com.ocs.portal.projection.attrvalue.AttrValueProjection;

@Mapper(componentModel = "spring")
public interface AttrValueMapper {
  AttrValueResponseDto toAttrValueResponseDto(AttrValueProjection projection);
}
