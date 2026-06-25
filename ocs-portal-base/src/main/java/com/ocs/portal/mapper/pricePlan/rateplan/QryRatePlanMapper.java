package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryRatePlanResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryRatePlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanMapper {
    QryRatePlanResponseDto toDto(QryRatePlanProjection projection);
}
