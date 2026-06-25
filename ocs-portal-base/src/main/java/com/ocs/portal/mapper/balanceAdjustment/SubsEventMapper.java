package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.priceplan.treshold.SubsEventDto;
import com.ocs.portal.projection.subs.SelectSubsEventProjection;

@Mapper(componentModel = "spring")
public interface SubsEventMapper {

  SubsEventDto toSubsEventDtoFromSelectSubEvent(SelectSubsEventProjection projection);

}
