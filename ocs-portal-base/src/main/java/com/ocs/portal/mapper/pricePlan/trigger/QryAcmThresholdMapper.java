package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.QryAcmThresholdResponseDto;
import com.ocs.portal.projection.pricePlan.trigger.QryAcmThresholdProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QryAcmThresholdMapper {
    QryAcmThresholdResponseDto toDto(QryAcmThresholdProjection projection);
    List<QryAcmThresholdResponseDto> toListDto(List<QryAcmThresholdProjection> projection);
}
