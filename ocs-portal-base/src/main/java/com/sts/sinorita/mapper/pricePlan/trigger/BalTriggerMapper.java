package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.BalTriggerParam;
import com.sts.sinorita.dto.response.trigger.BalTriggerResponseDto;
import com.sts.sinorita.dto.response.trigger.TriggerThresholdDto;
import com.sts.sinorita.projection.pricePlan.priceplanpackage.BalanceTresholdProjection;
import com.sts.sinorita.projection.pricePlan.trigger.BalTriggerProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalTriggerMapper {
    BalTriggerResponseDto toDto(BalTriggerProjection projection);

    List<TriggerThresholdDto> toListTriggerThresholdDto(List<BalanceTresholdProjection> projections);

    @Mapping(target = "threshold", ignore = true)
    @Mapping(target = "ratio", ignore = true)
    @Mapping(target = "balThresholdId", ignore = true)
    @Mapping(target = "triggerEffDate", source = "effDate")
    @Mapping(target = "priceEffDate", ignore = true)
    @Mapping(target = "pricePlanId", ignore = true)
    @Mapping(target = "reAttr", ignore = true)
    @Mapping(target = "subsEventList", ignore = true)
    @Mapping(target = "adviceList", ignore = true)
    BalTriggerParam toBalTriggerParams(BalTriggerProjection projection);
}
