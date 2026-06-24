package com.sts.sinorita.mapper.acct;


import com.sts.sinorita.dto.AcctItemTypeDetailDto;
import com.sts.sinorita.dto.request.AcctItemTypeDto;
import com.sts.sinorita.dto.request.AcctItemTypeQueryListDto;
import com.sts.sinorita.dto.request.accountConfig.AcctItemTypeUpdateDto;
import com.sts.sinorita.dto.response.QryBalanceTypeResponseDto;
import com.sts.sinorita.projection.QryBalanceTypeProjection;
import com.sts.sinorita.projection.acct.AcctItemTypeAscProjection;
import com.sts.sinorita.projection.acct.AcctItemTypeDetailProjection;
import com.sts.sinorita.projection.acct.AcctItemTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcctItemTypeMapper {
    AcctItemTypeQueryListDto dto(AcctItemTypeProjection acctItemTypeProjection);
    AcctItemTypeDetailDto acctItemTypeDetailDto(AcctItemTypeDetailProjection acctItemTypeDetailProjection);
    AcctItemTypeDto acctItemTypeDto(AcctItemTypeAscProjection acctItemTypeAscProjection);
    AcctItemTypeUpdateDto acctItemTypeUpdateDto(AcctItemTypeDetailProjection acctItemTypeDetailProjection2);
    QryBalanceTypeResponseDto toQryBalanceTypeResponseDto(QryBalanceTypeProjection qryBalanceTypeProjection);
    AcctItemTypeDto toAcctItemType (AcctItemTypeProjection acctItemTypeProjection);
}
