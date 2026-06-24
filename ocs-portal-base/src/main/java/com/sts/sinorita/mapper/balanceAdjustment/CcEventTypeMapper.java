package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.CcEventTypeDto;
import com.sts.sinorita.projection.balanceAdjustment.CcEventTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CcEventTypeMapper {
  CcEventTypeDto toCcEventTypeDto (CcEventTypeProjection projection);
}
