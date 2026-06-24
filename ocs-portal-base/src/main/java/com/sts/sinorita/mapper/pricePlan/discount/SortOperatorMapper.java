package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QrySortOperatorResponseDto;
import com.sts.sinorita.projection.pricePlan.discount.QrySortOperatorProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SortOperatorMapper {
    QrySortOperatorResponseDto toQrySortOperatorResponseDto(QrySortOperatorProjection projection);
}
