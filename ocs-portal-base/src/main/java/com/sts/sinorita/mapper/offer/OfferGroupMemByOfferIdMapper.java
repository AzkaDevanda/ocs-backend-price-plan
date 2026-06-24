package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.OfferGroupMemByOfferIdResponseDto;
import com.sts.sinorita.projection.offer.offergroupmem.OfferGroupMemByOfferIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferGroupMemByOfferIdMapper {
    OfferGroupMemByOfferIdResponseDto toDto(OfferGroupMemByOfferIdProjection projection);
}
