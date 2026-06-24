package com.sts.sinorita.svc.role.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PortalRequestDto {
  private Long portalId;
  private String portalName;
  private String url;
  private String extraUrl;
  private String contactChannelId;
  private String allowExternalAccess;
  private Long indexId;
  private String iconUrl;
  private String cssPath;
  private Long appId;
  private Long portletId;
  private Long spId;
  private String state;
  private LocalDateTime stateDate;
  private Long seq;
}
