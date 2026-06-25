package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryActiveZoneMapResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryActiveZoneMapProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryActiveZoneMapMapper {
    QryActiveZoneMapResponseDto toDto(QryActiveZoneMapProjection projection);
}
