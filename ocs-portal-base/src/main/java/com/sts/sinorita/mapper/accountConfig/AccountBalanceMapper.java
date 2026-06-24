package com.sts.sinorita.mapper.accountConfig;

import com.sts.sinorita.dto.request.accountConfig.AccountBalanceTypeRequestDto;
import com.sts.sinorita.dto.request.accountConfig.AcctBalanceTypeListDto;
import com.sts.sinorita.entity.AcctRes;
import com.sts.sinorita.projection.acct.AcctBalanceTypeListProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountBalanceMapper {
    AcctRes toEntityAcctRes(AccountBalanceTypeRequestDto dto);

    @Mapping(target = "acctResId", ignore = true)
    void modAcctRes(AccountBalanceTypeRequestDto dto, @MappingTarget AcctRes acctRes);

    AcctBalanceTypeListDto dto(AcctBalanceTypeListProjection projection);
}
