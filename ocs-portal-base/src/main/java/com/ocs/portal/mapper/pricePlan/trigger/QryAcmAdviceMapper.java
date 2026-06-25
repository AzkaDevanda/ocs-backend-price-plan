package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.QryAcmAdviceResponseDto;
import com.ocs.portal.projection.pricePlan.trigger.QryAcmAdviceProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmAdviceMapper {

    QryAcmAdviceResponseDto toDto(QryAcmAdviceProjection projection);

}
