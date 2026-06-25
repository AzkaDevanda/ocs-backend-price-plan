package com.ocs.portal.entity.mdbtt;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAL", schema = "MDBTT", indexes = {
    @Index(name = "IDX_BAL_ACCT_ID", columnList = "ACCT_ID")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "BAL_ID", nullable = false, precision = 18, scale = 0)
  private Long balId;

  @Column(name = "ACCT_ID", precision = 12, scale = 0)
  private Long acctId;

  @Column(name = "ACCT_RES_ID", precision = 9, scale = 0)
  private Long acctResId;

  @Column(name = "GROSS_BAL", precision = 20, scale = 0)
  private BigDecimal grossBal;

  @Column(name = "RESERVE_BAL", precision = 20, scale = 0)
  private BigDecimal reserveBal;

  @Column(name = "CONSUME_BAL", precision = 20, scale = 0)
  private BigDecimal consumeBal;

  @Column(name = "RATING_BAL", precision = 20, scale = 0)
  private BigDecimal ratingBal;

  @Column(name = "BILLING_BAL", precision = 20, scale = 0)
  private BigDecimal billingBal;

  @Column(name = "CEIL_LIMIT", precision = 20, scale = 0)
  private BigDecimal ceilLimit;

  @Column(name = "FLOOR_LIMIT", precision = 20, scale = 0)
  private BigDecimal floorLimit;

  @Column(name = "DAILY_CEIL_LIMIT", precision = 20, scale = 0)
  private BigDecimal dailyCeilLimit;

  @Column(name = "DAILY_FLOOR_LIMIT", precision = 20, scale = 0)
  private BigDecimal dailyFloorLimit;

  @Column(name = "PRIORITY", precision = 9, scale = 0)
  private Integer priority;

  @Column(name = "LAST_BAL", precision = 20, scale = 0)
  private BigDecimal lastBal;

  @Column(name = "LAST_RECHARGE", precision = 20, scale = 0)
  private BigDecimal lastRecharge;

  @Column(name = "BAL_USED", precision = 12, scale = 0)
  private Long balUsed;

  @Column(name = "INIT_BAL", precision = 20, scale = 0)
  private BigDecimal initBal;

  @Column(name = "SUBS_ID", precision = 12, scale = 0)
  private Long subsId;

  @Column(name = "USU_BAL", precision = 20, scale = 0)
  private BigDecimal usuBal;

  @Column(name = "ABS_EXP_OFFSET", precision = 12, scale = 0)
  private Long absExpOffset;

  @Column(name = "VAR_CEIL_LIMIT", precision = 20, scale = 0)
  private BigDecimal varCeilLimit;

  @Column(name = "CUST_ID", precision = 12, scale = 0)
  private Long custId;

  @Column(name = "EFF_DATE")
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "UPDATE_DATE")
  private LocalDateTime updateDate;

  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "IMSI", length = 60)
  private String imsi;

  @Column(name = "SHARE_PLUS_FLAG", length = 1)
  private String sharePlusFlag;

  @Column(name = "BAL_FLAGS", length = 8)
  private String balFlags;
}
