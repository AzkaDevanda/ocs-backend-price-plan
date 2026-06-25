package com.ocs.portal.svc.role.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QryNoDirMenuListResponseDto {
  private Long menuId;
  private String menuType;
  private String iconUrl;
  private String state;
  private LocalDateTime stateDate;
  private Long privId;
  private String privName;
  private String privCode;
  private String url;
  private String privEl;
  private String privType;
  private String menuName;
  private String menuUrl;
  private String comments;
  private String isHold;
  private Long spId;
  private Long appId;
  private String cdnUrl;
}
