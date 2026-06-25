package com.ocs.portal.mapper.offer;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.offerapplyrole.OfferApplyRoleForFishResponseDto;
import com.ocs.portal.projection.offer.offerapplyrole.OfferApplyRoleForFishProjection;

@Mapper(componentModel = "spring")
public interface OfferApplyRoleMapper {
  OfferApplyRoleForFishResponseDto toOfferApplyRoleForFishResponseDto(OfferApplyRoleForFishProjection projection);
}
