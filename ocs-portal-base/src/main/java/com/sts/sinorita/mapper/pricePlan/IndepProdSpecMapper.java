package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.dto.response.IndepProdSpecDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectIndepProdSpecProjection;
import com.sts.sinorita.projection.pricePlan.IndepProdSpecProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndepProdSpecMapper {
    IndepProdSpecDto toDto(IndepProdSpecProjection projection);
    IndepProdSpecDto toIndepProdSpecDtoFromSelectIndepProdSpec(SelectIndepProdSpecProjection projection);
}
