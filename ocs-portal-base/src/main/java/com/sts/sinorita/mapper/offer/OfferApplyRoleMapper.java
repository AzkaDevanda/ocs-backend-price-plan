package com.sts.sinorita.mapper.offer;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offerapplyrole.OfferApplyRoleForFishResponseDto;
import com.sts.sinorita.projection.offer.offerapplyrole.OfferApplyRoleForFishProjection;

@Mapper(componentModel = "spring")
public interface OfferApplyRoleMapper {
  OfferApplyRoleForFishResponseDto toOfferApplyRoleForFishResponseDto(OfferApplyRoleForFishProjection projection);
}
