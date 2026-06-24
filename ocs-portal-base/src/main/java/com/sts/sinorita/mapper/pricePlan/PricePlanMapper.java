package com.sts.sinorita.mapper.pricePlan;

import org.mapstruct.Mapper;

import com.sts.sinorita.dto.PricePlanExDto;
import com.sts.sinorita.projection.pricePlan.SelectPricePlanByPricePlanCodeProjection;

@Mapper(componentModel = "spring")
public interface PricePlanMapper {

  PricePlanExDto toPricePlanExDtoFromSelectPricePlanByPricePlanCode(
      SelectPricePlanByPricePlanCodeProjection projection);

}
