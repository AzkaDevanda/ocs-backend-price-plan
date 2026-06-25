package com.ocs.portal.svc.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ocs.portal.entity.BfmDir;
import com.ocs.portal.entity.BfmMenu;
import com.ocs.portal.svc.role.dto.request.dirMenu.DirRequestDto;
import com.ocs.portal.svc.role.dto.request.dirMenu.MenuRequestDto;
import com.ocs.portal.svc.role.dto.response.DirMenuResponseDto;
import com.ocs.portal.svc.role.projection.DirMenuProjection;

@Mapper(componentModel = "spring")
public interface BfmDirMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  DirMenuResponseDto toDirMenuResponseDto(DirMenuProjection projection);

  // =====> Entity
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "dirId", ignore = true)
  BfmDir toEntityBfmDir(DirRequestDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "menuId", ignore = true)
  BfmMenu toEntityBfmMenu(MenuRequestDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "menuId", ignore = true)
  void updateEntityBfmMenuFromMenuRequestDto(MenuRequestDto dto, @MappingTarget BfmMenu entity);
}
