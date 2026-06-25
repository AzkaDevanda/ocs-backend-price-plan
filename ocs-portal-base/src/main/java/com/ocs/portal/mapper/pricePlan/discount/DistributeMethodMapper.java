package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.DistributeMethodDto;
import com.ocs.portal.projection.pricePlan.discount.DistributeMethodProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistributeMethodMapper {
    DistributeMethodDto DistributeMethodDto(DistributeMethodProjection distributeMethodProjection);
}
