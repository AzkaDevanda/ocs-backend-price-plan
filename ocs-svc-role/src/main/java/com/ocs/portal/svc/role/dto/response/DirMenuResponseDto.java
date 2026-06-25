package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

@Data
public class DirMenuResponseDto {
  private Long menuId;
  private Long dirId;
  private String privName;
  private String privCode;
  private String url;
  private String iconUrl;
  private String isHold;
  private Long seq;
  private String isAuthorized;
}
