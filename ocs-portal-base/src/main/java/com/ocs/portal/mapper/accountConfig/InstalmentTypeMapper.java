package com.ocs.portal.mapper.accountConfig;

import java.util.List;

import com.ocs.portal.dto.response.accountconfig.GetInstalmentTypeDetail;
import com.ocs.portal.dto.response.accountconfig.ListIntalmentItemResponseDto;
import com.ocs.portal.dto.response.accountconfig.QryInstalmentType1ResponseDto;
import com.ocs.portal.dto.response.accountconfig.QryInstalmentTypeItemByInstalmentTypeIdResponseDto;
import com.ocs.portal.projection.accountConfig.QryInstalmentItemByTypeIDProjection;
import com.ocs.portal.projection.accountConfig.QryInstalmentType1Projection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ocs.portal.projection.accountConfig.QryInstalmentTypeDetailProjection;
import com.ocs.portal.projection.accountConfig.QryInstalmentTypeItemByIntalmentTypeIdProjection;

@Mapper(componentModel = "spring")
public interface InstalmentTypeMapper {
    QryInstalmentType1ResponseDto toDtoListInstalmentType(QryInstalmentType1Projection projection);

    @Mapping(target = "instalmentTypeId", source = "id")
    @Mapping(target = "stateDate", expression = "java(projection.getStateDate() != null ? projection.getStateDate().atStartOfDay() : null)")
    @Mapping(target = "instalmentItems", ignore = true)
    @Mapping(target = "appliedAccountItemType", ignore = true)
    GetInstalmentTypeDetail toDto(QryInstalmentTypeDetailProjection projection);

    List<ListIntalmentItemResponseDto> toItemDtoList(List<QryInstalmentItemByTypeIDProjection> projectionList);

    List<QryInstalmentTypeItemByInstalmentTypeIdResponseDto> toItemDtoList2(
            List<QryInstalmentTypeItemByIntalmentTypeIdProjection> projectionList);
}
