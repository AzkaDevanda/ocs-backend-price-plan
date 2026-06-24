package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.DepositTypeDto;
import com.sts.sinorita.projection.accountConfig.QryDepositType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositTypeMapper {
    DepositTypeDto depositType (QryDepositType qryDepositType);
}
