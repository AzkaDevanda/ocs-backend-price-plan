package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.QryAcmSubsEventResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryAcmSubsEventProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmSubsEventMapper {

    QryAcmSubsEventResponseDto toDto(QryAcmSubsEventProjection projection);

}
