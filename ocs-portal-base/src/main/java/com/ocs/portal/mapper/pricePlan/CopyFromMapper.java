package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.request.priceplan.CopyFromDto;
import com.ocs.portal.projection.pricePlan.CopyFromProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CopyFromMapper {
    CopyFromDto toDto(CopyFromProjection projection);
}
