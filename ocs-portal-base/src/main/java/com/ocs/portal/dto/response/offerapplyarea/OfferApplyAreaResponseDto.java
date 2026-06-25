package com.ocs.portal.dto.response.offerapplyarea;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferApplyAreaResponseDto {
  private Integer offerId;
  private Integer areaId;
  private Integer spId;
  private String excludeFlag;
  private Integer parentId;
  private String areaName;
  private String conditionName;
  private String comments;
  private String areaCode;
}