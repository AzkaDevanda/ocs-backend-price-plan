package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.BalSubsEventDto;
import com.ocs.portal.projection.pricePlan.BalSubsEventProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalSubsEventMapper {
    BalSubsEventDto toDto(BalSubsEventProjection projection);
}
