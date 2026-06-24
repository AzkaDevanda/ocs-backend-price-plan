package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.QryPriceVerResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryPriceVerProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryPriceVerMapper {
    QryPriceVerResponseDto toDto(QryPriceVerProjection projection);
}
