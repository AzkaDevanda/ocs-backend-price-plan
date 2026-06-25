package com.ocs.portal.mapper.offer;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.offercatgmem.PriceOfferListByCatgIdResponseDto;
import com.ocs.portal.projection.offer.offercatgmem.PriceOfferListByCatgIdProjection;

@Mapper(componentModel = "spring")
public interface OfferCatgMemMapper {
  PriceOfferListByCatgIdResponseDto toPriceOfferListByCatgIdResponseDto(PriceOfferListByCatgIdProjection projection);
}
