package com.ocs.portal.mapper.pricePlan.trigger;


import com.ocs.portal.dto.response.trigger.NotifyParamsIdDto;
import com.ocs.portal.dto.response.trigger.NotifyParamsListDto;
import com.ocs.portal.projection.trigger.NotifyParamsIdProjection;
import com.ocs.portal.projection.trigger.NotifyParamsListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyParamsMapper {
    NotifyParamsIdDto dto(NotifyParamsIdProjection notifyParamsProjection);
    NotifyParamsListDto notifyParamsListDto(NotifyParamsListProjection notifyParamsListProjection);
}
