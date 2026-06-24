package com.sts.sinorita.mapper.depend.dependprodspec;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.dependprodspec.DependProdWithNetworkTypeResponseDto;
import com.sts.sinorita.projection.depend.dependprodspec.DependProdWithNetworkTypeProjection;

@Mapper(componentModel = "spring")
public interface DependProdSpecMapper {
  DependProdWithNetworkTypeResponseDto toDependProdWithNetworkTypeResponseDto(DependProdWithNetworkTypeProjection projection);
}
