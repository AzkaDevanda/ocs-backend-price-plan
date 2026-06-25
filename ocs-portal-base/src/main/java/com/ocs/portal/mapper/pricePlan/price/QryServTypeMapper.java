package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.trigger.QryServTypeResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryServTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryServTypeMapper {
    QryServTypeResponseDto toDto(QryServTypeProjection projection);
}
