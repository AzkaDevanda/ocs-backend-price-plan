package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryDisctCalcMethodResponseDto;
import com.sts.sinorita.projection.pricePlan.discount.QryDisctCalcMethodProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisctCalcMethodMapper {
    QryDisctCalcMethodResponseDto toQryDisctCalcMethodResponseDto(QryDisctCalcMethodProjection projection);

}
