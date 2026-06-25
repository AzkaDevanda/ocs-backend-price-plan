package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryTabDpDtResponseDto;
import com.ocs.portal.projection.pricePlan.discount.QryTabDpDtProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpDetailMapper {
    QryTabDpDtResponseDto toQryTabDpDtResponseDto(QryTabDpDtProjection projection);
}
