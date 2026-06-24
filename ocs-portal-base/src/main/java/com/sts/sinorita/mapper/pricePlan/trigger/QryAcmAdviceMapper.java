package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.QryAcmAdviceResponseDto;
import com.sts.sinorita.projection.pricePlan.trigger.QryAcmAdviceProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmAdviceMapper {

    QryAcmAdviceResponseDto toDto(QryAcmAdviceProjection projection);

}
