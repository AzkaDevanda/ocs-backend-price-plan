package com.ocs.portal.mapper.pricePlan;

import com.ocs.portal.entity.OfferVer;
import com.ocs.portal.projection.pricePlan.OfferVerProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferVerMappers {
   OfferVer toOfferVer(OfferVerProjection list);
}
