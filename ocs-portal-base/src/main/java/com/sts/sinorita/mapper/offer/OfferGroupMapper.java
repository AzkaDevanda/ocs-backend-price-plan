package com.sts.sinorita.mapper.offer;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.response.offergroup.OfferGroupAndMemberResponseDto;
import com.sts.sinorita.projection.offer.offergroup.OfferGroupAndMemberProjection;

@Mapper(componentModel = "spring")
public interface OfferGroupMapper {
  OfferGroupAndMemberResponseDto toOfferGroupAndMemberResponseDto(OfferGroupAndMemberProjection projection);
}
