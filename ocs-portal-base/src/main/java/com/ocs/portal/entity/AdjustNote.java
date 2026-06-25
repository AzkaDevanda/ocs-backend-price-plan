package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ADJUST_NOTE")
public class AdjustNote {
  @Id
  @Size(max = 255)
  @Column(name = "VOUCHER", nullable = false)
  private String voucher;

  @Column(name = "ACCT_ID")
  private Long acctId;

  @Column(name = "CUST_ID")
  private Long custId;

  @NotNull
  @Column(name = "CHARGE", nullable = false)
  private Long charge;

  @Column(name = "EFF_DATE")
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @NotNull
  @Column(name = "PARTY_TYPE", nullable = false)
  private Character partyType;

  @Size(max = 30)
  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @NotNull
  @Column(name = "AUDIT_PARTY_TYPE", nullable = false)
  private Character auditPartyType;

  @Size(max = 30)
  @Column(name = "AUDIT_PARTY_CODE", length = 30)
  private String auditPartyCode;

  @Column(name = "ADJUST_REASON_ID")
  private Short adjustReasonId;

  @Size(max = 3000)
  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "STATE")
  private Character state;

  @Column(name = "STATE_DATE")
  private LocalDate stateDate;

  @Column(name = "SP_ID")
  private Integer spId;

}