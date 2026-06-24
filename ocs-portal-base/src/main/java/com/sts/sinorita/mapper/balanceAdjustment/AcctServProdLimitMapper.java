package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctServProdLimitDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServProdLimitListProjection;

@Mapper(componentModel = "spring")
public interface AcctServProdLimitMapper {

  AcctServProdLimitDto toAcctServProdLimitDtoFromSeleAcctServProdLimitDto(SelectAcctServProdLimitListProjection projection);

}
