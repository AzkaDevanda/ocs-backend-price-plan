package com.ocs.portal.svc.role.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ocs.portal.entity.pot.BfmPortal;
import com.ocs.portal.svc.role.dto.request.PortalRequestDto;
import com.ocs.portal.svc.role.dto.request.roles.DirMenusDto;
import com.ocs.portal.svc.role.dto.response.QryPortalListByMenuIdResponseDto;
import com.ocs.portal.svc.role.projection.DirMenusProjection;
import com.ocs.portal.svc.role.projection.QryPortalListByMenuIdProjection;

@Mapper(componentModel = "spring")
public interface BfmPortalMapper {
  QryPortalListByMenuIdResponseDto toQryPortalListByMenuIdResponseDto(QryPortalListByMenuIdProjection projection);
  
  // ======> Entity
  @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
  @Mapping(target = "portalId", ignore = true)
  BfmPortal toEntityBfmPortal(PortalRequestDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "portalId", ignore = true)
  void updateEntityBfmPortalFromPortalRequestDto(PortalRequestDto dto, @MappingTarget BfmPortal entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(target = "portalId", ignore = true)
  List<DirMenusDto> toDirMenus(List<DirMenusProjection> dirMenusProjection);
}

// {
// "portalName": "Test",
// "url": "main.html",
// "extraUrl": "124324234",
// "contactChannelId": "",
// "allowExternalAccess": "",
// "indexId": 1
// }

// [
// {
// "partyId": 111065,
// "seq": 1487,
// "oldSeq": 1367
// },
// {
// "partyId": 111181,
// "seq": 1367,
// "oldSeq": 1487
// }
// ]
