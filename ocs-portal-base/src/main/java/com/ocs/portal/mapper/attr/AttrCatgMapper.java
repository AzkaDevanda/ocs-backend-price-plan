package com.ocs.portal.mapper.attr;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.attr.SelectAttrCatgResponseDto;
import com.ocs.portal.projection.SelectAttrCatgProjection;

@Mapper(componentModel = "spring")
public interface AttrCatgMapper {

  SelectAttrCatgResponseDto toSelectAttrCatgResponseDto(SelectAttrCatgProjection projection);

}
