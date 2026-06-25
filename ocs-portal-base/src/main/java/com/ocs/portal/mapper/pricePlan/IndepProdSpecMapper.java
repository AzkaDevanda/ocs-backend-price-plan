package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.dto.response.IndepProdSpecDto;
import com.ocs.portal.projection.balanceAdjustment.SelectIndepProdSpecProjection;
import com.ocs.portal.projection.pricePlan.IndepProdSpecProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndepProdSpecMapper {
    IndepProdSpecDto toDto(IndepProdSpecProjection projection);
    IndepProdSpecDto toIndepProdSpecDtoFromSelectIndepProdSpec(SelectIndepProdSpecProjection projection);
}
