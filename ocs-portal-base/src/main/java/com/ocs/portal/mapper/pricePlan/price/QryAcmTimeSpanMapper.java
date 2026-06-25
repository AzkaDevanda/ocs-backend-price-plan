package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.QryAcmTimeSpanResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryAcmTimeSpanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmTimeSpanMapper {
    QryAcmTimeSpanResponseDto toDto(QryAcmTimeSpanProjection projection);
}
