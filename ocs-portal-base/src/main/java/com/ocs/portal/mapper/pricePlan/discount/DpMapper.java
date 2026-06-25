package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.DpDto;
import com.ocs.portal.dto.response.discount.QryDpByPkDto;
import com.ocs.portal.projection.pricePlan.discount.DpProjection;
import com.ocs.portal.projection.pricePlan.discount.QryDpByPkProjection;
import com.ocs.portal.utils.LobMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LobMapper.class})
public interface DpMapper {
    DpDto dpDto(DpProjection dpProjection);
    QryDpByPkDto qryDpByPkDto(QryDpByPkProjection qryDpByPkProjection);
}
