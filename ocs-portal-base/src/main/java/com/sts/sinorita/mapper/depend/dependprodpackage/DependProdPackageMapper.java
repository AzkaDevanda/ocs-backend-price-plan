package com.sts.sinorita.mapper.depend.dependprodpackage;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.dependprodpackage.DependProdJoinPackageResponseDto;
import com.sts.sinorita.projection.depend.dependprodpackage.DependProdJoinPackageProjection;

@Mapper(componentModel = "spring")
public interface DependProdPackageMapper {
  DependProdJoinPackageResponseDto toDependProdJoinPackageResponseDto(DependProdJoinPackageProjection projection);
}
