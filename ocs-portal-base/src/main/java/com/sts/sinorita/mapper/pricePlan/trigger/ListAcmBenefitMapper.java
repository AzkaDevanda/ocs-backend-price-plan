package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.ListAcmBenefitResponseDto;
import com.sts.sinorita.projection.pricePlan.trigger.AcmBenefitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListAcmBenefitMapper {
    ListAcmBenefitResponseDto toDto(AcmBenefitProjection projection);
}
