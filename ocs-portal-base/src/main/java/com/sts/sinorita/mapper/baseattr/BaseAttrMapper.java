package com.sts.sinorita.mapper.baseattr;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.baseattr.AttrDetailResponseDto;
import com.sts.sinorita.projection.baseattr.AttrDetailProjection;

@Mapper(componentModel = "spring")
public interface BaseAttrMapper {
  AttrDetailResponseDto toAttrDetailResponseDto(AttrDetailProjection projection);
}
