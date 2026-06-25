package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.OfferDto;
import com.ocs.portal.dto.request.offer.OfferRequestDto;
import com.ocs.portal.dto.response.discount.QryMemberAliasResponseDto;
import com.ocs.portal.dto.response.offer.OfferByNameResponseDto;
import com.ocs.portal.entity.Offer;
import com.ocs.portal.projection.offer.OfferByNameProjection;
import com.ocs.portal.projection.offer.SelectProdSpecByProdIdProjection;
import com.ocs.portal.projection.pricePlan.discount.QryMemberAliasProjection;
import org.mapstruct.Mapper;
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
