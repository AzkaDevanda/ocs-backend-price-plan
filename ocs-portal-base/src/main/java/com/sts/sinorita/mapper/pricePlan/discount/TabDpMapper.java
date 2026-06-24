package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryTabDpCondGrpDtDto;
import com.sts.sinorita.dto.response.discount.QryTabDpInfoDto;
import com.sts.sinorita.projection.pricePlan.discount.QryTabDpInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpMapper {
    QryTabDpInfoDto qryTabDpInfoDto(QryTabDpInfoProjection qryTabDpInfoProjection);
}
