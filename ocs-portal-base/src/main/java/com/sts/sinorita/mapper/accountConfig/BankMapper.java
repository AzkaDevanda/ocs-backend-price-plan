package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.response.accountconfig.BankDto;
import com.sts.sinorita.projection.accountConfig.QryBankProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankDto bankDto (QryBankProjection qryBankProjection);
}
