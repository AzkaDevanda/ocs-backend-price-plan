package com.ocs.portal.mapper.pricePlan.price;

import com.ocs.portal.dto.response.priceVer.AcmLisResponseDto;
import com.ocs.portal.projection.pricePlan.price.AcmListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcmListMapper {
    AcmLisResponseDto toDto(AcmListProjection projection);
}
