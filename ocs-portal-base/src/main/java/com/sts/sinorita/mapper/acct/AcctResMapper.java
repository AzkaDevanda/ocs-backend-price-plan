package com.sts.sinorita.mapper.acct;

import com.sts.sinorita.dto.request.priceplan.AcctResNameListDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.AcctRes;
import com.sts.sinorita.projection.acct.AcctResProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcctResMapper {
  AcctResNameListDto dto (AcctResProjection acctResProjection);

  AcctResDto toDto (AcctRes entity);

  List<AcctResDto> toDtoList (List<AcctRes> entities);

  AcctResDto toAcctResDto (AcctResProjection acctResProjection);
}
