package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.OfferGroupMemByOfferIdResponseDto;
import com.ocs.portal.projection.offer.offergroupmem.OfferGroupMemByOfferIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferGroupMemByOfferIdMapper {
    OfferGroupMemByOfferIdResponseDto toDto(OfferGroupMemByOfferIdProjection projection);
}
