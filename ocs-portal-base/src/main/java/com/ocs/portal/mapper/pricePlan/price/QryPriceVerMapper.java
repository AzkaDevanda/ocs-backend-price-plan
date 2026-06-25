package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.QryPriceVerResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryPriceVerProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryPriceVerMapper {
    QryPriceVerResponseDto toDto(QryPriceVerProjection projection);
}
