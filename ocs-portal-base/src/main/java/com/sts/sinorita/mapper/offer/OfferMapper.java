package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.OfferDto;
import com.sts.sinorita.dto.request.offer.OfferRequestDto;
import com.sts.sinorita.dto.response.discount.QryMemberAliasResponseDto;
import com.sts.sinorita.dto.response.offer.OfferByNameResponseDto;
import com.sts.sinorita.entity.Offer;
import com.sts.sinorita.projection.offer.OfferByNameProjection;
import com.sts.sinorita.projection.offer.SelectProdSpecByProdIdProjection;
import com.sts.sinorita.projection.pricePlan.discount.QryMemberAliasProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OfferMapper {
  Offer toEntity (OfferRequestDto dto);

  OfferByNameResponseDto toOfferByNameResponseDto (OfferByNameProjection projection);

  void updateEntityFromDto (OfferRequestDto dto, @MappingTarget Offer entity);

  QryMemberAliasResponseDto toQryMemberAliasResponseDto (QryMemberAliasProjection projection);

  // @Mapping(target = "offerId", source = "id")
  // @Mapping(target = "specTime", ignore = true)
  OfferRequestDto toOfferRequestDto(Offer entity);

  OfferDto toOfferDtoFromSelectProdSpecByProdId(SelectProdSpecByProdIdProjection projection);
}
