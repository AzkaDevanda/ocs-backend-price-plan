package com.sts.sinorita.mapper.offer;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offercatg.RootCatgResponseDto;
import com.sts.sinorita.projection.offer.offercatg.RootCatgProjection;

@Mapper(componentModel = "spring")
public interface OfferCatgMapper {
  RootCatgResponseDto toDto(RootCatgProjection projection);
}
