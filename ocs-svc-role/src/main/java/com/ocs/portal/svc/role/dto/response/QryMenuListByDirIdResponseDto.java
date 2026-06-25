package com.ocs.portal.svc.role.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class QryMenuListByDirIdResponseDto {
  private Long menuId;
  private String iconUrl;
  private String state;
  private Date stateDate;
  private String menuName;
  private String menuUrl;
  private String comments;
  private String isHold;
  private Long spId;
  private Long appId;
}
