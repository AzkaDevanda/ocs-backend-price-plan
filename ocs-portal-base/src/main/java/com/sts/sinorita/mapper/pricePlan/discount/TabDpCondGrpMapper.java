package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.response.discount.QryTabDpCondGrpDtDto;
import com.sts.sinorita.projection.pricePlan.discount.QryTabDpCondGrpDtProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpCondGrpMapper {
    QryTabDpCondGrpDtDto qryTabDpCondGrpDtDto(QryTabDpCondGrpDtProjection qryTabDpCondGrpDtProjection);
}
