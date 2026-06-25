package com.ocs.portal.mapper.pricePlan.rateplan;

import com.ocs.portal.dto.response.rateplan.QryMappingResponseDto;
import com.ocs.portal.projection.pricePlan.rateplan.QryMappingProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryMappingMapper {
    QryMappingResponseDto toDto(QryMappingProjection projection);
}
