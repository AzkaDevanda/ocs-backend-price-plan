package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

@Data
public class PortalSeqRequestDto {
  private Long partyId;
  private Integer seq;
  private Integer oldSeq;
}
