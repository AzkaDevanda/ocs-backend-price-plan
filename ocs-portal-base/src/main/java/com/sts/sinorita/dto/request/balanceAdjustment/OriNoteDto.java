package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OriNoteDto {
  private Long noteId;

  private Long paymentMethodId;

  private Long bankId;

  private String voucher;

  private Long charge;

  private String checkNbr;

  private String checkOwnerName;

  private String checkIssueDate;

  private String checkExpDate;

  private String scratchCardPwd;

  private Long custId;

  private Long acctId;

  private Long spId;

  private Long bankCardType;

  private String certNbr;

  private String state;

  private Date stateDate;

  private String partyType;

  private String partyCode;

  private Date updateDate;

  private String auditPartyType;

  private String auditPartyCode;

  private String receivedState;

  private LocalDateTime receivedDate;

  private String recPartyType;

  private String recPartyCode;

  private Long oriAcctResId;

  private Long destAcctResId;

  private String comments;

  private Long destAmount;
}
