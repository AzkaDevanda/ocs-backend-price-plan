package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.AcmTriggerResponseDto;
import com.ocs.portal.projection.pricePlan.trigger.AcmTriggerProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AcmTriggerMapper {

    @Mapping(target = "accumulationType.accumulationTypeId", source = "accumulationTypeId")
    @Mapping(target = "accumulationType.accumulationType", source = "accumulationTypeName")
    @Mapping(target = "triggerMode.triggerModeId", source = "triggerModeId")
    @Mapping(target = "triggerMode.triggerMode", source = "triggerModeName")
    AcmTriggerResponseDto toDto(AcmTriggerProjection projection);
}
