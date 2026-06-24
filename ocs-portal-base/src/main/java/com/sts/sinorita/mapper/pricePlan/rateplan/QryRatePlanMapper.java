package com.sts.sinorita.mapper.pricePlan.rateplan;

import com.sts.sinorita.dto.response.rateplan.QryRatePlanResponseDto;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryRatePlanMapper {
    QryRatePlanResponseDto toDto(QryRatePlanProjection projection);
}
