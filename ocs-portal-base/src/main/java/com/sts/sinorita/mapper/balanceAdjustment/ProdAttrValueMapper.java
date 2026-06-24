package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.ProdAttrValueResponseDto;
import com.sts.sinorita.projection.balanceAdjustment.QryProdAttrValueByAttrCodeProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdAttrValueMapper {
  ProdAttrValueResponseDto qryProdAttrValueByAttrCodeToProdAttrValue (QryProdAttrValueByAttrCodeProjection projection);
}
