package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_REBATE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferRebate {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq_rebate")
  @SequenceGenerator(name = "offer_seq_rebate", sequenceName = "OFFER_REBATE_ID_SEQ", allocationSize = 1)
  @Column(name = "OFFER_REBATE_ID", nullable = false)
  private Integer id;

  @Column(name = "OFFER_ID")
  private Integer offerId;

  @Column(name = "OFFER_VER_ID", nullable = false)
  private Integer offerVerId;

  @Column(name = "REBATE_TYPE", length = 1)
  private Character rebateType;

  @Column(name = "REBATE_COUNT")
  private Integer rebateCount;

  @Column(name = "REBATE_SEQ")
  private Integer rebateSeq;

  @Column(name = "VALUE")
  private Integer value;

  @Column(name = "COMMENTS", length = 4000)
  private String comment;

  @Column(name = "SP_ID")
  private Integer spId;
}
