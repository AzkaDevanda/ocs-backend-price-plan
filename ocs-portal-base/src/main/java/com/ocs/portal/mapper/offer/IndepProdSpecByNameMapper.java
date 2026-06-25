package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.response.offer.IndepProdSpecByNameResponseDto;
import com.ocs.portal.projection.offer.IndepProdSpecByNameProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndepProdSpecByNameMapper {
    IndepProdSpecByNameResponseDto toDto(IndepProdSpecByNameProjection projection);
}
