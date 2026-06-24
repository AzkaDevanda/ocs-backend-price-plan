package com.sts.sinorita.mapper.pricePlan.trigger;


import com.sts.sinorita.dto.response.trigger.NotifyParamsIdDto;
import com.sts.sinorita.dto.response.trigger.NotifyParamsListDto;
import com.sts.sinorita.projection.trigger.NotifyParamsIdProjection;
import com.sts.sinorita.projection.trigger.NotifyParamsListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyParamsMapper {
    NotifyParamsIdDto dto(NotifyParamsIdProjection notifyParamsProjection);
    NotifyParamsListDto notifyParamsListDto(NotifyParamsListProjection notifyParamsListProjection);
}
