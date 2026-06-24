package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.GetDetailBenefitPriceProjection;
import com.sts.sinorita.dto.response.priceVer.GetDetailBenefitResponseDto;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LobMapper.class)
public interface GetDetailBenefitMapper {
    GetDetailBenefitResponseDto toDto(GetDetailBenefitPriceProjection projection);
}
