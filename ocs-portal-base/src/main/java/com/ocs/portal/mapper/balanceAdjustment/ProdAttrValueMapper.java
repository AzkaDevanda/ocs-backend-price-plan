package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.ProdAttrValueResponseDto;
import com.ocs.portal.projection.balanceAdjustment.QryProdAttrValueByAttrCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdAttrValueMapper {
  ProdAttrValueResponseDto qryProdAttrValueByAttrCodeToProdAttrValue (QryProdAttrValueByAttrCodeProjection projection);
}
