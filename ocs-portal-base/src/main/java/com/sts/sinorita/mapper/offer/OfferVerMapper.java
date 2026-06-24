package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offerver.OfferEffectiveVerByOfferIdResponseDto;
import com.sts.sinorita.dto.response.offerver.OfferVerByOfferIdResponseDto;
import com.sts.sinorita.projection.offer.OfferVerByOfferIdProjection;
import com.sts.sinorita.projection.offer.offerver.OfferEffectiveVerByOfferIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferVerMapper {
  OfferVerByOfferIdResponseDto toDto(OfferVerByOfferIdProjection projection);
  OfferEffectiveVerByOfferIdResponseDto toOfferEffectiveVerByOfferIdResponseDto(OfferEffectiveVerByOfferIdProjection projection);
}
