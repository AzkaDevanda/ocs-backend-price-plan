package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.OfferApplyStaffResponseDto;
import com.ocs.portal.projection.offer.OfferApplyStaffProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyStaffMapper {
    OfferApplyStaffResponseDto toDto(OfferApplyStaffProjection projection);
}
