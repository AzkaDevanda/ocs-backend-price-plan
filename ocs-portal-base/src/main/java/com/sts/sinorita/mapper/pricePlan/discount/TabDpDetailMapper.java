package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryTabDpDtResponseDto;
import com.sts.sinorita.projection.pricePlan.discount.QryTabDpDtProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpDetailMapper {
    QryTabDpDtResponseDto toQryTabDpDtResponseDto(QryTabDpDtProjection projection);
}
