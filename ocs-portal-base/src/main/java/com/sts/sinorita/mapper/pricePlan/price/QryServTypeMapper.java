package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.trigger.QryServTypeResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryServTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryServTypeMapper {
    QryServTypeResponseDto toDto(QryServTypeProjection projection);
}
