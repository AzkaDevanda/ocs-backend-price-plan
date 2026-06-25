package com.ocs.portal.dto.response.attrvalue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttrValueResponseDto {
  private Integer baseAttrId;
  private Integer attrValueId;
  private String valueMark;
  private String value;
  private Integer parentAttrValueId;
  private Integer parentAttrId;
  private String attrName;
  private String attrCode;
}
