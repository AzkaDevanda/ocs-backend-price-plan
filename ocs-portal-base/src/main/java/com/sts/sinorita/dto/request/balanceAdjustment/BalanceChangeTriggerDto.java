package com.sts.sinorita.dto.request.balanceAdjustment;

import java.time.LocalDateTime;

import com.sts.sinorita.dto.ExtAttrDto;

import lombok.Data;

@Data
public class BalanceChangeTriggerDto {
  private Long acctId;

  private AcctDto acctDto;

  private BalDto oldBasicBalDto;

  private BalDto newBasicBalDto;

  private Long[] activeSubsIdList;

  private SubsDto[] allSubsDtoList;

  private SubsDto subsDto;

  private String isNeedActiveSubs;

  private Boolean isNewConnection;

  private Long servType;

  private LocalDateTime dateTimeNow;

  private BalDto[] curBalDtoList;

  private BalDto[] oldBalDtoList;

  private BalDto[] addBalDtoList;

  private Boolean isCommitMdb;

  private ExtAttrDto extAttr;

  private ExtAttrDto extAttrOut;

  private String acctBookType;

  private Long acctBookId;

  private Long spId;

  private Long contactChannelId;

}
