package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

@Data
public class QryPortalListByMenuIdResponseDto {
  private String portalName;
  private Long portalId;
  private Long seq;
  private Long partyId;
  private Long parentId;
  private String type;
}
