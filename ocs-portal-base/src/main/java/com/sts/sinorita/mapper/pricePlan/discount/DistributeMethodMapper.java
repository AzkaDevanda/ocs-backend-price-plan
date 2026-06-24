package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.DistributeMethodDto;
import com.sts.sinorita.projection.pricePlan.discount.DistributeMethodProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistributeMethodMapper {
    DistributeMethodDto DistributeMethodDto(DistributeMethodProjection distributeMethodProjection);
}
