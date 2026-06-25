package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.GetDetailBenefitPriceProjection;
import com.ocs.portal.dto.response.priceVer.GetDetailBenefitResponseDto;
import com.ocs.portal.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LobMapper.class)
public interface GetDetailBenefitMapper {
    GetDetailBenefitResponseDto toDto(GetDetailBenefitPriceProjection projection);
}
