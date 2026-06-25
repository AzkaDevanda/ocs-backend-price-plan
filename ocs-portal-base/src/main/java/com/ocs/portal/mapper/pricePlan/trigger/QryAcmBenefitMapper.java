package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.QryAcmBenefitResponseDto;
import com.ocs.portal.projection.pricePlan.trigger.QryAcmBenefitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmBenefitMapper {
    QryAcmBenefitResponseDto toDto(QryAcmBenefitProjection projection);

}
