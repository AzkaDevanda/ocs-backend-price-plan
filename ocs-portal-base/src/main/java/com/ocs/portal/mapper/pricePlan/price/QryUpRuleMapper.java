package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.rankUp.QryUpRuleResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryUpRuleProjection;
import com.ocs.portal.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LobMapper.class })
public interface QryUpRuleMapper {
    QryUpRuleResponseDto toDto(QryUpRuleProjection qryUpRuleProjection);
}
