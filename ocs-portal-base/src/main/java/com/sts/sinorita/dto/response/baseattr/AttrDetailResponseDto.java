package com.sts.sinorita.dto.response.baseattr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttrDetailResponseDto {
  private Integer baseAttrId;
  private Integer inputType;
  private String nullable;
  private String comments;
  private String defaultValue;
  private String valueScript;
  private Integer textAttrId;
  private String dataType;
  private String mask;
  private String ruleScript;
  private String editable;
  private String exceptionMessage;
  private Integer minValue;
  private Integer maxValue;
}
