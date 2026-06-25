package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.balanceAdjustment.PpsDueStateSubsEventDto;
import com.ocs.portal.projection.balanceAdjustment.SelectSubsEventIdForBalanceChangeTriggerProjection;

@Mapper(componentModel = "spring")
public interface PpsDueStateSubsEventMapper {

  PpsDueStateSubsEventDto tPpsDueStateSubsEventDtoFromSelectSubsEventIdForBalanceChangeTrigger(
      SelectSubsEventIdForBalanceChangeTriggerProjection projection);

}
