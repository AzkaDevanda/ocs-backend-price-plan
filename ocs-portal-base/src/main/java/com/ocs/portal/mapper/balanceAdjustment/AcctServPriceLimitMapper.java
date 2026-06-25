package com.ocs.portal.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.request.balanceAdjustment.AcctServPriceLimitDto;
import com.ocs.portal.projection.balanceAdjustment.SelectAcctServPriceLimitListProjection;

@Mapper(componentModel = "spring")
public interface AcctServPriceLimitMapper {

  AcctServPriceLimitDto toAcctServPriceLimitDtoFromSelectAcctServPriceLimitList(SelectAcctServPriceLimitListProjection projection);

}
