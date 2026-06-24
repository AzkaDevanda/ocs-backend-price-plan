package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.QryAcmThresholdResponseDto;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmThresholdProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QryAcmThresholdMapper {
    QryAcmThresholdResponseDto toDto(QryAcmThresholdProjection projection);
    List<QryAcmThresholdResponseDto> toListDto(List<QryAcmThresholdProjection> projection);
}
