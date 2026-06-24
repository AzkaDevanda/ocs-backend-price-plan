package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.QryBaseAttrListByPKDto;
import com.sts.sinorita.dto.response.accountconfig.QryAttrDetailDto;
import com.sts.sinorita.dto.response.accountconfig.QryAttrListByCatgDto;
import com.sts.sinorita.dto.response.accountconfig.QryAttrValueDto;
import com.sts.sinorita.projection.accountConfig.QryAttrDetailProjection;
import com.sts.sinorita.projection.accountConfig.QryAttrListByCatgProjection;
import com.sts.sinorita.projection.accountConfig.QryAttrValueProjection;
import com.sts.sinorita.projection.accountConfig.QryBaseAttrListByPKProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountFeatureMapper {
    QryBaseAttrListByPKDto qryBaseAttrListByPKDto(QryBaseAttrListByPKProjection qryBaseAttrListByPKProjection);

    QryAttrListByCatgDto qryAttrListByCatgDto(QryAttrListByCatgProjection qryAttrListByCatgProjection);

    QryAttrDetailDto qryAttrDetailDto(QryAttrDetailProjection qryAttrDetailProjection);

    QryAttrValueDto qryAttrValueDto(QryAttrValueProjection qryAttrValueProjection);
}
