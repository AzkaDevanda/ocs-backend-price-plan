package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.request.offer.SubsPlanByIndepProdDto;
import com.ocs.portal.projection.offer.SubsPlanByIndepProdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPlanByIndepProdMapper {

    SubsPlanByIndepProdDto toDto(SubsPlanByIndepProdProjection projection);}
