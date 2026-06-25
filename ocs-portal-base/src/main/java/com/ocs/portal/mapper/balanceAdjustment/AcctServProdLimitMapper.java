package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.balanceAdjustment.AcctServProdLimitDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAcctServProdLimitListProjection;

@Mapper(componentModel = "spring")
public interface AcctServProdLimitMapper {

  AcctServProdLimitDto toAcctServProdLimitDtoFromSeleAcctServProdLimitDto(SelectAcctServProdLimitListProjection projection);

}
