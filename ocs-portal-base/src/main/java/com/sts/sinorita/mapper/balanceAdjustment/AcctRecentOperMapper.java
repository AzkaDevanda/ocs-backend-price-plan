package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctRecentOperDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctRecentOperProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctRecentOperMapper {
  AcctRecentOperDto toAcctRecentOperDto (SelectAcctRecentOperProjection projection);
}
