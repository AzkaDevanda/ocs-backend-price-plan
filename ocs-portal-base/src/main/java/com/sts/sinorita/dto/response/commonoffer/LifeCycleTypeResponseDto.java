package com.sts.sinorita.dto.response.commonoffer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LifeCycleTypeResponseDto {
  private String lifecycleType;
  private String lifecycleTypeName;
  private String comments;
  private String spId;
  private String extAttr;
}
