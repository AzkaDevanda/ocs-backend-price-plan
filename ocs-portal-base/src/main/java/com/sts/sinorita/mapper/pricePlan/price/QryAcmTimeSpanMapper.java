package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.QryAcmTimeSpanResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryAcmTimeSpanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcmTimeSpanMapper {
    QryAcmTimeSpanResponseDto toDto(QryAcmTimeSpanProjection projection);
}
