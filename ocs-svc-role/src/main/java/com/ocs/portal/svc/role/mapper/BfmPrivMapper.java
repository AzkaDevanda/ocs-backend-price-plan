package com.ocs.portal.svc.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import com.ocs.portal.entity.pot.BfmPriv;
import com.ocs.portal.svc.role.dto.request.dirMenu.PrivRequestDto;

@Mapper(componentModel = "spring")
public interface BfmPrivMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
  BfmPriv toEntityBfmPriv(PrivRequestDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
  PrivRequestDto toPrivRequestDto(BfmPriv entity);
}
