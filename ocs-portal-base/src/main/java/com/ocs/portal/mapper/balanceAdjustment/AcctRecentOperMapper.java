package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.AcctRecentOperDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAcctRecentOperProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctRecentOperMapper {
  AcctRecentOperDto toAcctRecentOperDto (SelectAcctRecentOperProjection projection);
}
