package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.OfferApplyOrgResponseDto;
import com.sts.sinorita.projection.offer.offerapplyorg.OfferApplyOrgProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyOrgMapper {
    OfferApplyOrgResponseDto toDto(OfferApplyOrgProjection projection);

}
