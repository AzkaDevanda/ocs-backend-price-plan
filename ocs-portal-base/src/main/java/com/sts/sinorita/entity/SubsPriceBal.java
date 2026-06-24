package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBS_PRICE_BAL", schema = "STS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubsPriceBal implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subs_price_bal_seq")
  @SequenceGenerator(name = "subs_price_bal_seq", sequenceName = "SUBS_PRICE_BAL_ID_SEQ", allocationSize = 1)
  @Column(name = "SUBS_PRICE_BAL_ID", nullable = false, precision = 12, scale = 0)
  private Long subsPriceBalId;

  @Column(name = "SUBS_UPP_INST_ID", nullable = false, precision = 15, scale = 0)
  private Long subsUppInstId;

  @Column(name = "SUBS_ID", nullable = false, precision = 12, scale = 0)
  private Long subsId;

  @Column(name = "PRICE_PLAN_ID", nullable = false, precision = 9, scale = 0)
  private Long pricePlanId;

  @Column(name = "BAL_ID", nullable = false, precision = 18, scale = 0)
  private Long balId;

  @Column(name = "INIT_BAL", nullable = false, precision = 18, scale = 0)
  private BigDecimal initBal;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "PRICE_ID", precision = 12, scale = 0)
  private Long priceId;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "OFFER_ID", precision = 9, scale = 0)
  private Long offerId;

  @Column(name = "PROD_ID", precision = 12, scale = 0)
  private Long prodId;

  @Column(name = "PART_ID", nullable = false, precision = 3, scale = 0)
  private Integer partId;

  @Column(name = "ACCT_RES_ID", precision = 9, scale = 0)
  private Long acctResId;
}