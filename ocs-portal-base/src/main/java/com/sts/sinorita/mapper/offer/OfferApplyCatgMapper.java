package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.projection.offer.OfferApplyCatgProjection;
import com.sts.sinorita.dto.response.offerapplycatg.OfferApplyCatgResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferApplyCatgMapper {
  OfferApplyCatgResponseDto toDto(OfferApplyCatgProjection projection);
}
