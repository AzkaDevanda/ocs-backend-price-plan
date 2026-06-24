package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.response.priceVer.AcmLisResponseDto;
import com.sts.sinorita.projection.pricePlan.price.AcmListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcmListMapper {
    AcmLisResponseDto toDto(AcmListProjection projection);
}
