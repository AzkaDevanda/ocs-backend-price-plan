package com.sts.sinorita.mapper.pricePlan;

import com.sts.sinorita.entity.OfferVer;
import com.sts.sinorita.projection.pricePlan.OfferVerProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferVerMappers {
   OfferVer toOfferVer(OfferVerProjection list);
}
