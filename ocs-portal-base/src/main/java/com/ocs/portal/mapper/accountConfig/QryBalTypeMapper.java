package com.ocs.portal.mapper.accountConfig;

import com.ocs.portal.dto.request.BalTypeSimpleDto;
import com.ocs.portal.dto.response.accountconfig.QryBalTypeResponseDto;
import com.ocs.portal.projection.accountConfig.QryBalTypeProjection;
import com.ocs.portal.projection.acct.BalTypeAcctResProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryBalTypeMapper {
    QryBalTypeResponseDto toDto(QryBalTypeProjection projection);
    BalTypeSimpleDto toSimpleDto(BalTypeAcctResProjection balTypeAcctResprojection);
}
