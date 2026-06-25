package com.ocs.portal.dto.request.balanceAdjustment.adjust;

import java.time.LocalDateTime;
import java.util.List;

import com.ocs.portal.dto.request.balanceAdjustment.AcctDto;
import com.ocs.portal.dto.request.balanceAdjustment.BalDto;
import com.ocs.portal.dto.request.balanceAdjustment.SubsDto;
import com.ocs.portal.dto.request.balanceAdjustment.ExtAttrBalanceChangeTrigger;

import lombok.Data;

@Data
public class BalanceChangeTriggerDict {

  private Long subsId;

  private String isLoanFreeEff;

  private Long charge;

  private Long spId;

  private SubsDto subsDto;

  private Boolean isNewConnection;

  private Integer servType;

  private String isNeedActiveSubs;

  private Long acctId;

  private AcctDto acct;

  private BalDto newBasicBal;

  private BalDto oldBasicBal;

  public SubsDto[] allSubsDtoList;

  private List<BalDto> addBalList;

  public LocalDateTime dateTimeNow;

  private BalDto[] curBalList;

  private BalDto[] oldBalList;

  private List<Long> activeSubsIdList;

  private String acctBookType;

  private Long AcctBookId;

  private String contactChannelId;

  private Long subsEventId;

  private Long onceFee;

  private ExtAttrBalanceChangeTrigger extAttr;

}
