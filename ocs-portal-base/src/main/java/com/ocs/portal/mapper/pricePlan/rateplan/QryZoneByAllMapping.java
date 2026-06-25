package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryZoneByAllResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryZoneByAllProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryZoneByAllMapping {
    QryZoneByAllResponseDto toDto(QryZoneByAllProjection projection);
}
