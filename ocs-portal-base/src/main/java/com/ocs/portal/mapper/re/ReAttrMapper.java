package com.ocs.portal.mapper.re;

import com.ocs.portal.dto.response.priceplan.QryReAttrByReAttrTypeResponseDto;
import com.ocs.portal.entity.ReAttr;
import com.ocs.portal.projection.pricePlan.QryReAttrByReAttrTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReAttrMapper {
    QryReAttrByReAttrTypeResponseDto toDto(QryReAttrByReAttrTypeProjection qryReAttrByReAttrTypeProjection);
}
