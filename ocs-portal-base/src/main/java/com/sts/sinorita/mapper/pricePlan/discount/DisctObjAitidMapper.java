package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryDisctObjInfoDto;
import com.sts.sinorita.projection.pricePlan.discount.QryDisctObjInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctObjAitidMapper {
    QryDisctObjInfoDto QryDisctObjInfoDto(QryDisctObjInfoProjection qryDisctObjInfoProjection);
}
