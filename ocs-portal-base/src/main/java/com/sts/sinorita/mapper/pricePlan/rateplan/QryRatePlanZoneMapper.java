package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryRatePlanZoneResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanZoneProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanZoneMapper {
    QryRatePlanZoneResponseDto toDto(QryRatePlanZoneProjection projection);
}
