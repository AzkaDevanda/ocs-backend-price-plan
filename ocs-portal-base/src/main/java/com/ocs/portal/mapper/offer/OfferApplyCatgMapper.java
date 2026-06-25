package com.ocs.portal.mapper.offer;

import com.ocs.portal.projection.offer.OfferApplyCatgProjection;
import com.ocs.portal.dto.response.offerapplycatg.OfferApplyCatgResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyCatgMapper {
  OfferApplyCatgResponseDto toDto(OfferApplyCatgProjection projection);
}
