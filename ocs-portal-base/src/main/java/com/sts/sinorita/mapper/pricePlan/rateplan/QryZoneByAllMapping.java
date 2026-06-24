package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryZoneByAllResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryZoneByAllProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryZoneByAllMapping {
    QryZoneByAllResponseDto toDto(QryZoneByAllProjection projection);
}
