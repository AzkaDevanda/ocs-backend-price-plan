package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.response.offer.IndepProdSpecByNameResponseDto;
import com.sts.sinorita.projection.offer.IndepProdSpecByNameProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndepProdSpecByNameMapper {
    IndepProdSpecByNameResponseDto toDto(IndepProdSpecByNameProjection projection);
}
