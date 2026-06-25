package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.QryAcmRefResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryAcmRefProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmRefMapper {

    QryAcmRefResponseDto toDto(QryAcmRefProjection projection);

}
