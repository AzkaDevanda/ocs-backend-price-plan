package com.sts.sinorita.dto.response.dependprodspec;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DependProdWithNetworkTypeResponseDto {
  private Integer dependProdSpecId;
  private String offerName;
  private String duplicateFlag;
  private String networkType;
  private String networkTypeName;
  private String isPackage;
}
