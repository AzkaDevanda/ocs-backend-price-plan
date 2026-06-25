package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offerapplychannel.OfferApplyChannelResponseDto;
import com.ocs.portal.projection.offer.offerapplychannel.OfferApplyChannelProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyChannelMapper {
    OfferApplyChannelResponseDto toDto(OfferApplyChannelProjection projection);
}
