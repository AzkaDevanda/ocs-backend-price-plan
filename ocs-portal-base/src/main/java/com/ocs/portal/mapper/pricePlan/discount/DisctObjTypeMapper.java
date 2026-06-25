package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryDisctObjTypeResponseDto;
import com.ocs.portal.projection.pricePlan.discount.QryDisctObjTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctObjTypeMapper {
    QryDisctObjTypeResponseDto toQryDisctObjTypeResponseDto(QryDisctObjTypeProjection projection);
}
