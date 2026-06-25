package com.ocs.portal.mapper.baseattr;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.baseattr.AttrDetailResponseDto;
import com.ocs.portal.projection.baseattr.AttrDetailProjection;

@Mapper(componentModel = "spring")
public interface BaseAttrMapper {
  AttrDetailResponseDto toAttrDetailResponseDto(AttrDetailProjection projection);
}
