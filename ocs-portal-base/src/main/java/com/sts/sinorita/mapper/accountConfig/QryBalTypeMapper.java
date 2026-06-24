package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.BalTypeSimpleDto;
import com.sts.sinorita.dto.response.accountconfig.QryBalTypeResponseDto;
import com.sts.sinorita.projection.accountConfig.QryBalTypeProjection;
import com.sts.sinorita.projection.acct.BalTypeAcctResProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QryBalTypeMapper {
    QryBalTypeResponseDto toDto(QryBalTypeProjection projection);
    BalTypeSimpleDto toSimpleDto(BalTypeAcctResProjection balTypeAcctResprojection);
}
