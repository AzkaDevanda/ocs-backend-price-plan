package com.sts.sinorita.mapper.offer;

import com.sts.sinorita.dto.request.balanceAdjustment.OfferAttrDto;
import com.sts.sinorita.dto.response.offeattr.OfferAttrResponseDto;
import com.sts.sinorita.entity.OfferAttr;
import com.sts.sinorita.projection.balanceAdjustment.SelectOfferAttrByAttrCodeProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectOfferAttrListByAttrCodeProjection;
import com.sts.sinorita.projection.offer.offerattr.OfferAttrProjection;
import com.sts.sinorita.projection.offer.offerattr.SelectOfferAttrByOfferIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferAttrMapper {
  OfferAttrResponseDto toDtoOfferAttr (OfferAttrProjection projection);

  OfferAttrDto selectOfferAttrByAttrCodeToOfferAttrDto (SelectOfferAttrByAttrCodeProjection projection);

  OfferAttrDto selectOfferAttrListByAttrCodeToListOfferAttrDto (SelectOfferAttrListByAttrCodeProjection projection);

  //  List<OfferAttrDto> selectOfferAttrListByAttrCodeToListOfferAttrDto (List<SelectOfferAttrListByAttrCodeProjection> projections);

  OfferAttrDto tOfferAttrDtoFromSelectOfferAttrByOfferId(SelectOfferAttrByOfferIdProjection projection);
}
