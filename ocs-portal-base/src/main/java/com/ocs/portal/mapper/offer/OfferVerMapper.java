package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offerver.OfferEffectiveVerByOfferIdResponseDto;
import com.ocs.portal.dto.response.offerver.OfferVerByOfferIdResponseDto;
import com.ocs.portal.projection.offer.OfferVerByOfferIdProjection;
import com.ocs.portal.projection.offer.offerver.OfferEffectiveVerByOfferIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferVerMapper {
  OfferVerByOfferIdResponseDto toDto(OfferVerByOfferIdProjection projection);
  OfferEffectiveVerByOfferIdResponseDto toOfferEffectiveVerByOfferIdResponseDto(OfferEffectiveVerByOfferIdProjection projection);
}
