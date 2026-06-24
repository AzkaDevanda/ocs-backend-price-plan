package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.DpDto;
import com.sts.sinorita.dto.response.discount.QryDpByPkDto;
import com.sts.sinorita.projection.pricePlan.discount.DpProjection;
import com.sts.sinorita.projection.pricePlan.discount.QryDpByPkProjection;
import com.sts.sinorita.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LobMapper.class})
public interface DpMapper {
    DpDto dpDto(DpProjection dpProjection);
    QryDpByPkDto qryDpByPkDto(QryDpByPkProjection qryDpByPkProjection);
}
