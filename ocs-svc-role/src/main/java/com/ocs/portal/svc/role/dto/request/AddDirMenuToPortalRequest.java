package com.ocs.portal.svc.role.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class AddDirMenuToPortalRequest {
  private Long portalId;
  private Long spId;
  private Long parentId;
  private List<PartyMenuRequestDto> partyList;
}
