package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryDisctCalcMethodResponseDto;
import com.ocs.portal.projection.pricePlan.discount.QryDisctCalcMethodProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctCalcMethodMapper {
    QryDisctCalcMethodResponseDto toQryDisctCalcMethodResponseDto(QryDisctCalcMethodProjection projection);

}
