package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryMappingResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryMappingProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryMappingMapper {
    QryMappingResponseDto toDto(QryMappingProjection projection);
}
