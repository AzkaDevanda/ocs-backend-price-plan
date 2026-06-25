package com.ocs.portal.mapper.offer;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.offerapplyarea.OfferApplyAreaResponseDto;
import com.ocs.portal.projection.offer.offerapplyarea.OfferApplyAreaProjection;

@Mapper(componentModel = "spring")
public interface OfferApplyAreaMapper {
  OfferApplyAreaResponseDto toOfferApplyAreaResponseDto(OfferApplyAreaProjection projection);
}
