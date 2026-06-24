package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.QryEventBenefitResponseDto;
import com.sts.sinorita.projection.pricePlan.price.QryEventBenefitProjection;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LobMapper.class})
public interface QryEventBenefitMapper {
    QryEventBenefitResponseDto toDto(QryEventBenefitProjection projection);
}
