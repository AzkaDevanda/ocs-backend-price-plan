package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.response.priceplan.QryAcctPricePlanResponseDto;
import com.ocs.portal.projection.acct.QryAcctPricePlanProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryAcctPricePlanMapper {
    QryAcctPricePlanResponseDto toDto(QryAcctPricePlanProjection projection);
}
