package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BAL", schema = "MDBTT")
public class Bal {

  @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bal_id_seq")
//  @SequenceGenerator(name = "bal_id_seq", sequenceName = "BAL_ID_SEQ", allocationSize = 1)
  @Column(name = "BAL_ID", nullable = false)
  private Long balId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "ACCT_RES_ID")
    private Long acctResId;

    @Column(name = "GROSS_BAL")
    private BigDecimal grossBal;

    @Column(name = "RESERVE_BAL")
    private BigDecimal reserveBal;

    @Column(name = "CONSUME_BAL")
    private BigDecimal consumeBal;

    @Column(name = "RATING_BAL")
    private BigDecimal ratingBal;

    @Column(name = "BILLING_BAL")
    private BigDecimal billingBal;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CEIL_LIMIT")
    private BigDecimal ceilLimit;

    @Column(name = "FLOOR_LIMIT")
    private BigDecimal floorLimit;

    @Column(name = "DAILY_CEIL_LIMIT")
    private BigDecimal dailyCeilLimit;

    @Column(name = "DAILY_FLOOR_LIMIT")
    private BigDecimal dailyFloorLimit;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "LAST_BAL")
    private BigDecimal lastBal;

    @Column(name = "LAST_RECHARGE")
    private BigDecimal lastRecharge;

    @Column(name = "BAL_USED")
    private Integer balUsed;

    @Column(name = "INIT_BAL")
    private BigDecimal initBal;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "IMSI")
    private String imsi;

    @Column(name = "SHARE_PLUS_FLAG")
    private String sharePlusFlag;

    @Column(name = "USU_BAL")
    private BigDecimal usuBal;

    @Column(name = "ABS_EXP_OFFSET")
    private Integer absExpOffset;

    @Column(name = "VAR_CEIL_LIMIT")
    private BigDecimal varCeilLimit;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "BAL_FLAGS")
    private String balFlags;

    @Column(name = "CUST_ID")
    private Long custId;

    // @Column(name = "M")
    // private String m;

  @Transient
  private Long ceilLimitCharge;

  @Transient
  private String operationType;

  @Transient
  private String checkMode;

  @Transient
  private String postCheckMode;

  @Transient
  private Long balAdjustSeconds;

  @Transient
  private String acctResName;

  @Transient
  private String acctResComments;

  @Transient
  private AcctResDto acctResDto;

  @Transient
  private Long routingId;

  @Transient
  private Long preSuttleBal;

  @Transient
  private Long balCode;

  @Transient
  private Long changeInitBal;

  @Transient
  private BalAcctItemType[] balAcctItemTypeList;

  @Transient
  private Long consumeBalCharge;

  @Transient
  private String refAttr;

  @Transient
  private Long realBal = 0L;
  @Transient
  private Long usedBal = 0L;
  @Transient
  private Long charge = 0L;


}
