package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.BalSubsEventDto;
import com.sts.sinorita.projection.pricePlan.BalSubsEventProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalSubsEventMapper {
    BalSubsEventDto toDto(BalSubsEventProjection projection);
}
