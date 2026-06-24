package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.request.OfferDependProdByNameDto;
import com.sts.sinorita.dto.request.offer.*;
import com.sts.sinorita.projection.offer.offerrela.*;
import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offerrela.OfferRelaAsOriResponseDto;
import com.sts.sinorita.dto.response.offerrela.OfferRelaResponseDto;

@Mapper(componentModel = "spring")
public interface OfferRelaMapper {
  OfferRelaAsOriResponseDto toOfferRelaAsOriResponseDto(OfferRelaAsOriProjection projection);
  OfferDependProdByNameDto dto(OfferDependProdSpecProjection dependProdProjection);
  DependOfferListByCatgDto dto2(DependOfferListByCatgProjection dependOfferProjection);
  DependProdDetailByOfferIdDto dto3(DependProdDetailByOfferIdProjection dependProdDetailProjection);
  IndepOfferForRelaDto dto4(IndepOfferForRelaProjection dependProdProjection);
  PricePlanForRelaDto dto5(PricePlanForRelaProjection pricePlanForRelaProjection);
  SubsPlanOfferForRelaDto dto6(SubsPlanOfferForRelaProjection projection);
  BundleOfferForRelaDto dto7(BundleOfferForRelaProjection projection);
  OfferGroupForRelaDto dto8(OfferGroupForRelaProjection projection);
  GoodsOfferForRelaDto dto9(GoodsOfferForRelaProjection projection);
  DependOfferForRelaDto dto10(DependOfferForRelaProjection dependProdProjection);
  OfferRelaResponseDto toOfferRelaResponseDto(OfferRelaProjection projection);
}
