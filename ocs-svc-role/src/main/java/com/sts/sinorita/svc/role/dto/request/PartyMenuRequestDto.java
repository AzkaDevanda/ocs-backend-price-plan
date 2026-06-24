package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

@Data
public class PartyMenuRequestDto {
  private Long partyId;
  private String type;
  private String partyName;
  private Long parentId;
  private String url;

  private Boolean addCascade; // from ADD_CASCADE
}
