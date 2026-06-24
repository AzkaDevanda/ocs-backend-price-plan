package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.OfferRebateResponseDto;
import com.sts.sinorita.projection.offer.offerrebate.OfferRebateProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferRebateMapper {
    OfferRebateResponseDto toDto(OfferRebateProjection offerRebateProjection);
}
