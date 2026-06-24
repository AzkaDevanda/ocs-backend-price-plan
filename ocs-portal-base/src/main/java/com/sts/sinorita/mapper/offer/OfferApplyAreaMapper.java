package com.sts.sinorita.mapper.offer;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offerapplyarea.OfferApplyAreaResponseDto;
import com.sts.sinorita.projection.offer.offerapplyarea.OfferApplyAreaProjection;

@Mapper(componentModel = "spring")
public interface OfferApplyAreaMapper {
  OfferApplyAreaResponseDto toOfferApplyAreaResponseDto(OfferApplyAreaProjection projection);
}
