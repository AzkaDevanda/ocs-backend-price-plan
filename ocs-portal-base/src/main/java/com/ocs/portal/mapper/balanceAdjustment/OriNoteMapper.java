package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.OriNoteDto;
import com.ocs.portal.projection.balanceAdjustment.QryOriNoteProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OriNoteMapper {
  OriNoteDto toOriNoteDto (QryOriNoteProjection projection);
}
