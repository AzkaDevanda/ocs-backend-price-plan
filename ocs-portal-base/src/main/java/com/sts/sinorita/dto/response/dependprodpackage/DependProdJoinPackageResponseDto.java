package com.sts.sinorita.dto.response.dependprodpackage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DependProdJoinPackageResponseDto {
  private Integer dependProdSpecId;
  private Integer offerId;
  private String offerName;
  private String networkType;
  private String networkTypeName;
  private String defaultFlag;
}
