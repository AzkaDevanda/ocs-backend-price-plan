package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.response.balanceAdjustment.AcmCycleTypeDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAcmCycleTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcmCycleTypeMapper {
  AcmCycleTypeDto toAcmCycleTypeDto (SelectAcmCycleTypeProjection projection);
}
