package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QrySortOperatorResponseDto;
import com.ocs.portal.projection.pricePlan.discount.QrySortOperatorProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SortOperatorMapper {
    QrySortOperatorResponseDto toQrySortOperatorResponseDto(QrySortOperatorProjection projection);
}
