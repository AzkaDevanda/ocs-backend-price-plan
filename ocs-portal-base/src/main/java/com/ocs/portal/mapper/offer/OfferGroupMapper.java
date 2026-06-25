package com.ocs.portal.mapper.offer;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.response.offergroup.OfferGroupAndMemberResponseDto;
import com.ocs.portal.projection.offer.offergroup.OfferGroupAndMemberProjection;

@Mapper(componentModel = "spring")
public interface OfferGroupMapper {
  OfferGroupAndMemberResponseDto toOfferGroupAndMemberResponseDto(OfferGroupAndMemberProjection projection);
}
