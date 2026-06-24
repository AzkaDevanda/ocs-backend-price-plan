package com.sts.sinorita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCT_BOOK", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcctBook {
  @Id
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_book_seq_generator")
  // @SequenceGenerator(name = "acct_book_seq_generator", sequenceName = "ACCT_BOOK_ID_SEQ", allocationSize = 1)
  private Long acctBookId;

  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Column(name = "ACCT_RES_ID", nullable = false)
  private Long acctResId;

  @Column(name = "ACCT_BOOK_TYPE", nullable = false, length = 1)
  private String acctBookType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "PRE_BALANCE", nullable = false, precision = 18, scale = 0)
  private BigDecimal preBalance;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PRE_EXP_DATE")
  private LocalDateTime preExpDate;

  @Column(name = "CHARGE", nullable = false, precision = 18, scale = 0)
  private BigDecimal charge;

  @Column(name = "BILL_ID")
  private Long billId;

  @Column(name = "PARTY_TYPE", nullable = false, length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @Column(name = "PRE_SUTTLE_BAL", precision = 18, scale = 0)
  private BigDecimal preSuttleBal;

  @Column(name = "SECONDS")
  private Long seconds;

  @Column(name = "BAL_ID", nullable = false)
  private Long balId;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Integer contactChannelId;

  @Column(name = "EVENT_PAYMENT_ID")
  private Long eventPaymentId;

  @Column(name = "GL_CODE", length = 30)
  private String glCode;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PRE_EFF_DATE")
  private LocalDateTime preEffDate;

  @Column(name = "EFF_SECONDS")
  private Long effSeconds;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "REF_ATTR", length = 4000)
  private String refAttr;

  @Column(name = "ORG_ID")
  private Integer orgId;

  @Column(name = "EVENT_INST_ID")
  private Long eventInstId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @PrePersist
  public void prePersist() {
    if (partId == null) {
      partId = (short) java.time.LocalDate.now().getMonthValue();
    }
  }

  @PreUpdate
  public void preUpdate() {
    if (partId == null) {
      partId = (short) java.time.LocalDate.now().getMonthValue();
    }
  }
}