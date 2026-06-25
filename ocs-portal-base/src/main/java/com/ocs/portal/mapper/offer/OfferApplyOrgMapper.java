package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.OfferApplyOrgResponseDto;
import com.ocs.portal.projection.offer.offerapplyorg.OfferApplyOrgProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyOrgMapper {
    OfferApplyOrgResponseDto toDto(OfferApplyOrgProjection projection);

}
