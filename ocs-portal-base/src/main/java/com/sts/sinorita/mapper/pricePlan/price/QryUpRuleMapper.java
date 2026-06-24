package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.rankUp.QryUpRuleResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryUpRuleProjection;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { LobMapper.class })
public interface QryUpRuleMapper {
    QryUpRuleResponseDto toDto(QryUpRuleProjection qryUpRuleProjection);
}
