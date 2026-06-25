package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.AcctDepositBalDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAcctDepositBalByAcctIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctDepositBalMapper {

  AcctDepositBalDto toSelectAcctDepositBalByAcctIdResponseDto (SelectAcctDepositBalByAcctIdProjection projection);
}
