package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CcEventDto {
  public Long eventId;

  public Long eventFormatId;

  public Long subsId;

  public String prefix;

  public String accNbr;

  public LocalDateTime createdDate;

  public String state;

  public LocalDateTime stateDate;

  public String eventParam;
  public String comments;
  public Long spId;
  private String eventParam1;
}
