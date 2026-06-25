package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.QryAcmRuleResponseDto;
import com.ocs.portal.projection.pricePlan.price.QryAcmRuleProjection;
import com.ocs.portal.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {LobMapper.class})
public interface QryAcmRuleMapper {
    QryAcmRuleResponseDto toDto(QryAcmRuleProjection projection);
    
}
