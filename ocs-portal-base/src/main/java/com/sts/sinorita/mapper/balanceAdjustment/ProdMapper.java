package com.sts.sinorita.mapper.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.ProdDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ProdResponseDto;
import com.sts.sinorita.projection.balanceAdjustment.FindProdBySubsIdProjection;
import com.sts.sinorita.projection.balanceAdjustment.QryProdByIdProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectProdBySubsIdProjection;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdMapper {

  ProdResponseDto qryProdByIdToProdDto (QryProdByIdProjection projection);

  ProdDto toProdDtoFromFindProdBySubsId(FindProdBySubsIdProjection projection);

  ProdDto toProdDtoFromSelectProdDtoBySubsId(SelectProdBySubsIdProjection projection);

  ProdDto qryProdIdToProdDto(QryProdByIdProjection projection);

}
