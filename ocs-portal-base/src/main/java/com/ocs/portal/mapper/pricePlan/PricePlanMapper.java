package com.ocs.portal.mapper.pricePlan;

import org.mapstruct.Mapper;

import com.ocs.portal.dto.PricePlanExDto;
import com.ocs.portal.projection.pricePlan.SelectPricePlanByPricePlanCodeProjection;

@Mapper(componentModel = "spring")
public interface PricePlanMapper {

  PricePlanExDto toPricePlanExDtoFromSelectPricePlanByPricePlanCode(
      SelectPricePlanByPricePlanCodeProjection projection);

}
