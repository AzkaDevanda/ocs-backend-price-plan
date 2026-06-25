package com.ocs.portal.mapper.offer;

import com.ocs.portal.dto.request.balanceAdjustment.OfferAttrDto;
import com.ocs.portal.dto.response.offeattr.OfferAttrResponseDto;
import com.ocs.portal.projection.balanceAdjustment.SelectOfferAttrByAttrCodeProjection;
import com.ocs.portal.projection.balanceAdjustment.SelectOfferAttrListByAttrCodeProjection;
import com.ocs.portal.projection.offer.offerattr.OfferAttrProjection;
import com.ocs.portal.projection.offer.offerattr.SelectOfferAttrByOfferIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferAttrMapper {
  OfferAttrResponseDto toDtoOfferAttr (OfferAttrProjection projection);

  OfferAttrDto selectOfferAttrByAttrCodeToOfferAttrDto (SelectOfferAttrByAttrCodeProjection projection);

  OfferAttrDto selectOfferAttrListByAttrCodeToListOfferAttrDto (SelectOfferAttrListByAttrCodeProjection projection);

  //  List<OfferAttrDto> selectOfferAttrListByAttrCodeToListOfferAttrDto (List<SelectOfferAttrListByAttrCodeProjection> projections);

  OfferAttrDto tOfferAttrDtoFromSelectOfferAttrByOfferId(SelectOfferAttrByOfferIdProjection projection);
}
