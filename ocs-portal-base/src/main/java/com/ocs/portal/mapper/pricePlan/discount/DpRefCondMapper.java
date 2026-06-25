package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.DpRefCondDto;
import com.ocs.portal.projection.pricePlan.discount.DpRefCondProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DpRefCondMapper {
    DpRefCondDto dpRefCondDto(DpRefCondProjection dpRefCondProjection);
}
