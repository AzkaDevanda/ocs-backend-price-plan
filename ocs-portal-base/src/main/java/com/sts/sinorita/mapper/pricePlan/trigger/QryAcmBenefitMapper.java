package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.QryAcmBenefitResponseDto;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmBenefitProjection;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QryAcmBenefitMapper {
    QryAcmBenefitResponseDto toDto(QryAcmBenefitProjection projection);

}
