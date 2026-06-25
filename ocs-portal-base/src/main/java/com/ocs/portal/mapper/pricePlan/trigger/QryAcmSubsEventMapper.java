package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.QryAcmSubsEventResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryAcmSubsEventProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmSubsEventMapper {

    QryAcmSubsEventResponseDto toDto(QryAcmSubsEventProjection projection);

}
