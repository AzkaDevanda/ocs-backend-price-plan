package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.response.priceplan.QryAcctPricePlanResponseDto;
import com.sts.sinorita.projection.acct.QryAcctPricePlanProjection;
import com.sts.sinorita.projection.pricePlan.rateplan.QryZoneByAllProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcctPricePlanMapper {
    QryAcctPricePlanResponseDto toDto(QryAcctPricePlanProjection projection);
}
