package com.sts.sinorita.mapper.pricePlan.trigger;

import com.sts.sinorita.dto.response.trigger.QrySubBalTypeLimitResponseDto;
import com.sts.sinorita.projection.trigger.QrySubBalTypeLimitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubBalTypeLimitMapper {
    QrySubBalTypeLimitResponseDto toQrySubBalTypeLimitResponseDto(QrySubBalTypeLimitProjection subBalTypeLimitProjection);
}
