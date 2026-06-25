package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.QryEventBenefitResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryEventBenefitProjection;
import com.ocs.portal.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LobMapper.class})
public interface QryEventBenefitMapper {
    QryEventBenefitResponseDto toDto(QryEventBenefitProjection projection);
}
