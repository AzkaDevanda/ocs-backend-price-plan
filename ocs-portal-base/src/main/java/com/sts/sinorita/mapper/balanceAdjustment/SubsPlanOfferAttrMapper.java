package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.SubsPlanOfferAttrResponseDto;
import com.sts.sinorita.projection.balanceAdjustment.SelectSubsPlanOfferAttrByAttrCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPlanOfferAttrMapper {
  SubsPlanOfferAttrResponseDto toSubsPlanOfferAttrResponseDto (SelectSubsPlanOfferAttrByAttrCodeProjection projection);
}
