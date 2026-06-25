package com.ocs.portal.mapper.pricePlan.discount;

import com.ocs.portal.dto.TabDpTypeDto;
import com.ocs.portal.dto.request.DpTypesDto;
import com.ocs.portal.projection.pricePlan.discount.DpTypeProjection;
import com.ocs.portal.projection.pricePlan.discount.TabDpTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DpTypeMapper {
    DpTypesDto DpTypeDto(DpTypeProjection dpTypeProjection);
    TabDpTypeDto TabDpTypeDto(TabDpTypeProjection tabDpTypeProjection);
}
