package com.sts.sinorita.mapper.pricePlan.discount;

import com.sts.sinorita.dto.TabDpTypeDto;
import com.sts.sinorita.dto.request.DpTypesDto;
import com.sts.sinorita.entity.DpType;
import com.sts.sinorita.projection.pricePlan.discount.DpTypeProjection;
import com.sts.sinorita.projection.pricePlan.discount.TabDpTypeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DpTypeMapper {
    DpTypesDto DpTypeDto(DpTypeProjection dpTypeProjection);
    TabDpTypeDto TabDpTypeDto(TabDpTypeProjection tabDpTypeProjection);
}
