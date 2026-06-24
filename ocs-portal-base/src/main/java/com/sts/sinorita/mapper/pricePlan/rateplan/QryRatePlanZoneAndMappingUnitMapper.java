package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryRatePlanZoneAndMappingUnitResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanZoneAndMappingUnitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanZoneAndMappingUnitMapper {
    QryRatePlanZoneAndMappingUnitResponseDto toDto(QryRatePlanZoneAndMappingUnitProjection projection);
}
