package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryTabDpCondGrpDtDto;
import com.ocs.portal.projection.pricePlan.discount.QryTabDpCondGrpDtProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpCondGrpMapper {
    QryTabDpCondGrpDtDto qryTabDpCondGrpDtDto(QryTabDpCondGrpDtProjection qryTabDpCondGrpDtProjection);
}
