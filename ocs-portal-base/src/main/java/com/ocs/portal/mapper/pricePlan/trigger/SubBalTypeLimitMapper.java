package com.ocs.portal.mapper.pricePlan.trigger;

import com.ocs.portal.dto.response.trigger.QrySubBalTypeLimitResponseDto;
import com.ocs.portal.projection.trigger.QrySubBalTypeLimitProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubBalTypeLimitMapper {
    QrySubBalTypeLimitResponseDto toQrySubBalTypeLimitResponseDto(QrySubBalTypeLimitProjection subBalTypeLimitProjection);
}
