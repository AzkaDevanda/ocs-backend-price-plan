package com.ocs.portal.mapper.balanceAdjustment;

import com.ocs.portal.dto.request.balanceAdjustment.ProdDto;
import com.ocs.portal.dto.request.balanceAdjustment.ProdResponseDto;
import com.ocs.portal.projection.balanceAdjustment.FindProdBySubsIdProjection;
import com.ocs.portal.projection.balanceAdjustment.QryProdByIdProjection;
import com.ocs.portal.projection.balanceAdjustment.SelectProdBySubsIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdMapper {

  ProdResponseDto qryProdByIdToProdDto (QryProdByIdProjection projection);

  ProdDto toProdDtoFromFindProdBySubsId(FindProdBySubsIdProjection projection);

  ProdDto toProdDtoFromSelectProdDtoBySubsId(SelectProdBySubsIdProjection projection);

  ProdDto qryProdIdToProdDto(QryProdByIdProjection projection);

}
