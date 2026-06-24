package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.OfferSubPlanResponse;
import com.sts.sinorita.projection.offer.OfferSubsPlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferSubPlanMapper {
    OfferSubPlanResponse toDto(OfferSubsPlanProjection projection);
}
