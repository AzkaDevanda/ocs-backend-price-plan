package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.DpRefCondDto;
import com.sts.sinorita.projection.pricePlan.discount.DpRefCondProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DpRefCondMapper {
    DpRefCondDto dpRefCondDto(DpRefCondProjection dpRefCondProjection);
}
