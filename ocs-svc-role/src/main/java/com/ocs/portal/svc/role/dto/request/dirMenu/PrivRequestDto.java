package com.ocs.portal.svc.role.dto.request.dirMenu;

import lombok.Data;

@Data
public class PrivRequestDto {
  private Long appId;
  private Long privId;
  private String privType;
  private String privCode;
  private String privName;
  private String privEl;
  private String url;
  private String comments;
  private String isAuthorized;
  private String isHold;
  private Long spId;
}
