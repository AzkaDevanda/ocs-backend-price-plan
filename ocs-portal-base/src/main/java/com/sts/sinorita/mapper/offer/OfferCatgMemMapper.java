package com.sts.sinorita.mapper.offer;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offercatgmem.PriceOfferListByCatgIdResponseDto;
import com.sts.sinorita.projection.offer.offercatgmem.PriceOfferListByCatgIdProjection;

@Mapper(componentModel = "spring")
public interface OfferCatgMemMapper {
  PriceOfferListByCatgIdResponseDto toPriceOfferListByCatgIdResponseDto(PriceOfferListByCatgIdProjection projection);
}
