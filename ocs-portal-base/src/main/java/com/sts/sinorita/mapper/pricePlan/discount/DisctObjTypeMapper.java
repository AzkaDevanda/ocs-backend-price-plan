package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryDisctObjTypeResponseDto;
import com.sts.sinorita.projection.pricePlan.discount.QryDisctObjTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctObjTypeMapper {
    QryDisctObjTypeResponseDto toQryDisctObjTypeResponseDto(QryDisctObjTypeProjection projection);
}
