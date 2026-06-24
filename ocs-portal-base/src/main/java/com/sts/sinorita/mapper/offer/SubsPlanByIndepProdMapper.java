package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.request.offer.SubsPlanByIndepProdDto;
import com.sts.sinorita.projection.offer.SubsPlanByIndepProdProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPlanByIndepProdMapper {

    SubsPlanByIndepProdDto toDto(SubsPlanByIndepProdProjection projection);}
