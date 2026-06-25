package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ACCT_BOOK_RECONCILE")
public class AcctBookReconcile {
  @Id
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @NotNull
  @Column(name = "RECONCILE_STATE", nullable = false)
  private Boolean reconcileState = false;

  @Column(name = "LOG_ID")
  private Integer logId;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Short contactChannelId;

  @Size(max = 120)
  @Column(name = "PARTNER_SN", length = 120)
  private String partnerSn;

  @Column(name = "ACCT_BOOK_TYPE")
  private Boolean acctBookType;

  @Column(name = "CHARGE")
  private Long charge;

  @Column(name = "CREATED_DATE")
  private LocalDate createdDate;

  @Column(name = "PAYMENT_DATE")
  private LocalDate paymentDate;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Size(max = 255)
  @Column(name = "PARTNER_DETAIL")
  private String partnerDetail;

  @Size(max = 60)
  @Column(name = "TRANSACTION_TYPE", length = 60)
  private String transactionType;

  @Column(name = "PROCESS_STATE")
  private Boolean processState;

}