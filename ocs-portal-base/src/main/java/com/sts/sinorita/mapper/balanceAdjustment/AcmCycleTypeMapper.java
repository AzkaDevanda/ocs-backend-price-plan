package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.response.balanceAdjustment.AcmCycleTypeDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcmCycleTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcmCycleTypeMapper {
  AcmCycleTypeDto toAcmCycleTypeDto (SelectAcmCycleTypeProjection projection);
}
