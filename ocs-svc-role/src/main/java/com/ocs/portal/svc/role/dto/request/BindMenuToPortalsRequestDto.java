package com.ocs.portal.svc.role.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BindMenuToPortalsRequestDto {
  private List<PortalRequestDto> addPortalList;
  private List<PortalRequestDto> delPortalList;
  private Long spId;
  private Long portalId;
  private Long partyId;
  private Long parentId;
  private String type;
  private String state;
  private LocalDateTime stateDate;
  private Long seq;
}
