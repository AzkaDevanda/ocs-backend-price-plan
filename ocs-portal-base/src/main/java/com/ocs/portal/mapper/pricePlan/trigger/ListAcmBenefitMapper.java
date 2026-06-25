package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.ListAcmBenefitResponseDto;
import com.ocs.portal.projection.pricePlan.trigger.AcmBenefitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListAcmBenefitMapper {
    ListAcmBenefitResponseDto toDto(AcmBenefitProjection projection);
}
