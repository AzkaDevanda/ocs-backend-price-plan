package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.balanceAdjustment.PpsDueStateSubsEventDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectSubsEventIdForBalanceChangeTriggerProjection;

@Mapper(componentModel = "spring")
public interface PpsDueStateSubsEventMapper {

  PpsDueStateSubsEventDto tPpsDueStateSubsEventDtoFromSelectSubsEventIdForBalanceChangeTrigger(
      SelectSubsEventIdForBalanceChangeTriggerProjection projection);

}
