package com.sts.sinorita.mapper.balanceAdjustment;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.request.balanceAdjustment.AcctServPriceLimitDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServPriceLimitListProjection;

@Mapper(componentModel = "spring")
public interface AcctServPriceLimitMapper {

  AcctServPriceLimitDto toAcctServPriceLimitDtoFromSelectAcctServPriceLimitList(SelectAcctServPriceLimitListProjection projection);

}
