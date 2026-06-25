package com.ocs.portal.svc.role.mapper;

import org.mapstruct.Mapper;

import com.ocs.portal.svc.role.dto.response.QryMenuListByDirIdResponseDto;
import com.ocs.portal.svc.role.dto.response.QryNoDirMenuListResponseDto;
import com.ocs.portal.svc.role.projection.QryMenuListByDirIdProjection;
import com.ocs.portal.svc.role.projection.QryNoDirMenuListProjection;

@Mapper(componentModel = "spring")
public interface BfmMenuMapper {
  QryNoDirMenuListResponseDto toQryNoDirMenuListResponseDto(QryNoDirMenuListProjection projection);
  QryMenuListByDirIdResponseDto toQryMenuListByDirIdResponseDto(QryMenuListByDirIdProjection projection);
}
