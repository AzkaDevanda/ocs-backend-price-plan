package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.response.priceplan.QryReAttrByReAttrTypeResponseDto;
import com.sts.sinorita.projection.pricePlan.QryReAttrByReAttrTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryReAttrByReAttrTypeMapper {
    QryReAttrByReAttrTypeResponseDto toDto(QryReAttrByReAttrTypeProjection projection);
}
