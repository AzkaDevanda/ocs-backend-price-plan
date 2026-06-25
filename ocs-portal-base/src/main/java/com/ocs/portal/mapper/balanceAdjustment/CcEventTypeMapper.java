package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.CcEventTypeDto;
import com.ocs.portal.projection.balanceAdjustment.CcEventTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CcEventTypeMapper {
  CcEventTypeDto toCcEventTypeDto (CcEventTypeProjection projection);
}
