package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryRatePlanZoneAndMappingUnitResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryRatePlanZoneAndMappingUnitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanZoneAndMappingUnitMapper {
    QryRatePlanZoneAndMappingUnitResponseDto toDto(QryRatePlanZoneAndMappingUnitProjection projection);
}
