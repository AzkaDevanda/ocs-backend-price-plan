package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryRatePlanZoneResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryRatePlanZoneProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanZoneMapper {
    QryRatePlanZoneResponseDto toDto(QryRatePlanZoneProjection projection);
}
