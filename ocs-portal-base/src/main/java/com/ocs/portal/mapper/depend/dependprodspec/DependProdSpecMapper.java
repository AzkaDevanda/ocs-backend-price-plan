package com.ocs.portal.mapper.depend.dependprodspec;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.dependprodspec.DependProdWithNetworkTypeResponseDto;
import com.ocs.portal.projection.depend.dependprodspec.DependProdWithNetworkTypeProjection;

@Mapper(componentModel = "spring")
public interface DependProdSpecMapper {
  DependProdWithNetworkTypeResponseDto toDependProdWithNetworkTypeResponseDto(DependProdWithNetworkTypeProjection projection);
}
