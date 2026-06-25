package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.SubsDto;
import com.ocs.portal.projection.balanceAdjustment.SelectSubsProjection;
import com.ocs.portal.projection.subs.SelectSubsByAcctIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubsMapper {
  SubsDto toSubsDto(SelectSubsProjection projection);

  SubsDto toSubsDtoFromSelectLastSubs(SelectSubsProjection projection);

  SubsDto toSubsDtoFromSelectAllSubsByAcctId(SelectSubsByAcctIdProjection projection);

}
