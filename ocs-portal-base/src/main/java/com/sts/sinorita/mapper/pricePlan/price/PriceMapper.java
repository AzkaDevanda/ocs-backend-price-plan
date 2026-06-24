package com.sts.sinorita.mapper.pricePlan.price;

import com.sts.sinorita.dto.request.priceplan.PriceInfoDto;
import com.sts.sinorita.projection.pricePlan.price.PriceInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceInfoDto toDto(PriceInfoProjection priceInfoProjection);
}
