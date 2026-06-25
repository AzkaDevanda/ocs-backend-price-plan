package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.SubsPlanOfferAttrResponseDto;
import com.ocs.portal.projection.balanceAdjustment.SelectSubsPlanOfferAttrByAttrCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsPlanOfferAttrMapper {
  SubsPlanOfferAttrResponseDto toSubsPlanOfferAttrResponseDto (SelectSubsPlanOfferAttrByAttrCodeProjection projection);
}
