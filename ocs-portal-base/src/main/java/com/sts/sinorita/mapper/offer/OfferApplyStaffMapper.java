package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.OfferApplyStaffResponseDto;
import com.sts.sinorita.projection.offer.OfferApplyStaffProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyStaffMapper {
    OfferApplyStaffResponseDto toDto(OfferApplyStaffProjection projection);
}
