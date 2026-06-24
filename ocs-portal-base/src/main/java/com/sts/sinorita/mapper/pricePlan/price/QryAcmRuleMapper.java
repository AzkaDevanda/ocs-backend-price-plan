package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.QryAcmRuleResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryAcmRuleProjection;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = {LobMapper.class})
public interface QryAcmRuleMapper {
    QryAcmRuleResponseDto toDto(QryAcmRuleProjection projection);
    
}
