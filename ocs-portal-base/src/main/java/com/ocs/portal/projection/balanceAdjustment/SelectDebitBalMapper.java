package com.ocs.portal.projection.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.DebitBalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SelectDebitBalMapper {
  DebitBalDto toSelectDebitBalResponseDto (SelectDebitBalProjection projection);

}
