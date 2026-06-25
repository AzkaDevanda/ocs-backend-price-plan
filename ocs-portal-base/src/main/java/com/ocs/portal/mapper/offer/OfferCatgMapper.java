package com.ocs.portal.mapper.offer;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.offercatg.RootCatgResponseDto;
import com.ocs.portal.projection.offer.offercatg.RootCatgProjection;

@Mapper(componentModel = "spring")
public interface OfferCatgMapper {
  RootCatgResponseDto toDto(RootCatgProjection projection);
}
