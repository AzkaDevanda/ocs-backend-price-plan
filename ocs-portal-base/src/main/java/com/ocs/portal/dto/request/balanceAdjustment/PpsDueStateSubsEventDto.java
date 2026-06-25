package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class PpsDueStateSubsEventDto {
  private Long ppsDueStateSubsEventId;

  private String srcProdState;

  private String dueStateChange;

  private String expireDateChange;

  private Long subsEvent;

  private String unsuitChannelList;

  private String blockReason;
}
