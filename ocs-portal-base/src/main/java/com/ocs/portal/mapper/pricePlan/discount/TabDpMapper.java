package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.response.discount.QryTabDpInfoDto;
import com.ocs.portal.projection.pricePlan.discount.QryTabDpInfoProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TabDpMapper {
    QryTabDpInfoDto qryTabDpInfoDto(QryTabDpInfoProjection qryTabDpInfoProjection);
}
