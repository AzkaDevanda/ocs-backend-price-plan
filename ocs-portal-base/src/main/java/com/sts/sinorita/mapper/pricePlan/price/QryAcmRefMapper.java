package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.QryAcmRefResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryAcmRefProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmRefMapper {

    QryAcmRefResponseDto toDto(QryAcmRefProjection projection);

}
