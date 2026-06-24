package com.sts.sinorita.mapper.attr;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.attr.SelectAttrCatgResponseDto;
import com.sts.sinorita.projection.SelectAttrCatgProjection;

@Mapper(componentModel = "spring")
public interface AttrCatgMapper {

  SelectAttrCatgResponseDto toSelectAttrCatgResponseDto(SelectAttrCatgProjection projection);

}
