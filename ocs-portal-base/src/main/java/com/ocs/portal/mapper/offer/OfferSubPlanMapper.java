package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.OfferSubPlanResponse;
import com.ocs.portal.projection.offer.OfferSubsPlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferSubPlanMapper {
    OfferSubPlanResponse toDto(OfferSubsPlanProjection projection);
}
