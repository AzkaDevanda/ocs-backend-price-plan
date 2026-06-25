package com.ocs.portal.dto.response.offerapplyrole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferApplyRoleForFishResponseDto {
  private Integer offerId;
  private Integer roleId;
  private Integer spId;
  private String excludeFlag;
  private String roleName;
  private String conditionName;
  private String name;
  private String roleCode;
  private Integer appId;
  private String isLocked;
}
