package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.request.priceplan.CopyFromDto;
import com.sts.sinorita.projection.pricePlan.CopyFromProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CopyFromMapper {
    CopyFromDto toDto(CopyFromProjection projection);
}
