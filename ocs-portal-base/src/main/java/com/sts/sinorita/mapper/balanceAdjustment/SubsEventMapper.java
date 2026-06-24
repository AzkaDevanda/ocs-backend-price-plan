package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.priceplan.treshold.SubsEventDto;
import com.sts.sinorita.projection.subs.SelectSubsEventProjection;

@Mapper(componentModel = "spring")
public interface SubsEventMapper {

  SubsEventDto toSubsEventDtoFromSelectSubEvent(SelectSubsEventProjection projection);

}
