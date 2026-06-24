package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryActiveZoneMapResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryActiveZoneMapProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryActiveZoneMapMapper {
    QryActiveZoneMapResponseDto toDto(QryActiveZoneMapProjection projection);
}
