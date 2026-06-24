package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offerapplychannel.OfferApplyChannelResponseDto;
import com.sts.sinorita.projection.offer.offerapplychannel.OfferApplyChannelProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyChannelMapper {
    OfferApplyChannelResponseDto toDto(OfferApplyChannelProjection projection);
}
