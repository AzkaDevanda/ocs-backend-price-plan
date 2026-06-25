package com.ocs.portal.mapper.depend.dependprodpackage;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.dependprodpackage.DependProdJoinPackageResponseDto;
import com.ocs.portal.projection.depend.dependprodpackage.DependProdJoinPackageProjection;

@Mapper(componentModel = "spring")
public interface DependProdPackageMapper {
  DependProdJoinPackageResponseDto toDependProdJoinPackageResponseDto(DependProdJoinPackageProjection projection);
}
