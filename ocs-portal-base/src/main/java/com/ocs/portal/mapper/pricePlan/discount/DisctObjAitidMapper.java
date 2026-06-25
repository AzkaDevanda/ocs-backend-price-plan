package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryDisctObjInfoDto;
import com.ocs.portal.projection.pricePlan.discount.QryDisctObjInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctObjAitidMapper {
    QryDisctObjInfoDto QryDisctObjInfoDto(QryDisctObjInfoProjection qryDisctObjInfoProjection);
}
