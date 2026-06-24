package com.sts.sinorita.mapper.acct;

import com.sts.sinorita.dto.AcctIdentifyDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.BalanceChangeTriggerDict;
import com.sts.sinorita.dto.response.acct.QryAcctInfoResponseDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.projection.acct.AcctIdentifyProjection;
import com.sts.sinorita.projection.acct.QryAcctInfoProjection;
import com.sts.sinorita.projection.acct.SelectAcctDtoByAcctIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctMapper {
    QryAcctInfoResponseDto toQryAcctInfoResponseDto(QryAcctInfoProjection projection);

    // BalanceChangeTriggerDict tobaBalanceChangeTriggerDict(AcctDto acctDto);

    AcctDto toAcctDtoFromSelectAcctDtoByAcctId(SelectAcctDtoByAcctIdProjection projection);

    AcctIdentifyDto toAcctIdentifyDto(AcctIdentifyProjection projection);

}
