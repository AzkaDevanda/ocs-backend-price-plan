package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.response.priceplan.QryReAttrByReAttrTypeResponseDto;
import com.ocs.portal.projection.pricePlan.QryReAttrByReAttrTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryReAttrByReAttrTypeMapper {
    QryReAttrByReAttrTypeResponseDto toDto(QryReAttrByReAttrTypeProjection projection);
}
