package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctDepositBalDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctDepositBalByAcctIdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctDepositBalMapper {

  AcctDepositBalDto toSelectAcctDepositBalByAcctIdResponseDto (SelectAcctDepositBalByAcctIdProjection projection);
}
