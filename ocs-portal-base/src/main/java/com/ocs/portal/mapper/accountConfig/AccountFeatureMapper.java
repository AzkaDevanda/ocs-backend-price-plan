package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.accountConfig.QryBaseAttrListByPKDto;
import com.ocs.portal.dto.response.accountconfig.QryAttrDetailDto;
import com.ocs.portal.dto.response.accountconfig.QryAttrListByCatgDto;
import com.ocs.portal.dto.response.accountconfig.QryAttrValueDto;
import com.ocs.portal.projection.accountConfig.QryAttrDetailProjection;
import com.ocs.portal.projection.accountConfig.QryAttrListByCatgProjection;
import com.ocs.portal.projection.accountConfig.QryAttrValueProjection;
import com.ocs.portal.projection.accountConfig.QryBaseAttrListByPKProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountFeatureMapper {
    QryBaseAttrListByPKDto qryBaseAttrListByPKDto(QryBaseAttrListByPKProjection qryBaseAttrListByPKProjection);

    QryAttrListByCatgDto qryAttrListByCatgDto(QryAttrListByCatgProjection qryAttrListByCatgProjection);

    QryAttrDetailDto qryAttrDetailDto(QryAttrDetailProjection qryAttrDetailProjection);

    QryAttrValueDto qryAttrValueDto(QryAttrValueProjection qryAttrValueProjection);
}
