package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.OfferRebateResponseDto;
import com.ocs.portal.projection.offer.offerrebate.OfferRebateProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferRebateMapper {
    OfferRebateResponseDto toDto(OfferRebateProjection offerRebateProjection);
}
