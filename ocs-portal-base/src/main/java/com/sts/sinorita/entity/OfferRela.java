package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_RELA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferRela {
  @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_rela_seq")
  // @SequenceGenerator(name = "offer_rela_seq", sequenceName = "OFFER_RELA_ID_SEQ", allocationSize = 1)
  @Column(name = "OFFER_RELA_ID", nullable = false)
  private Integer id;

  @Column(name = "ORI_OFFER_ID")
  private Integer oriOfferId;

  @Column(name = "ORI_OFFER_GROUP_ID")
  private Integer oriOfferGroupId;

  @Column(name = "RELA_TYPE", nullable = false)
  private Integer relaType;

  @Column(name = "DEST_OFFER_ID")
  private Integer destOfferId;

  @Column(name = "DEST_OFFER_GROUP_ID")
  private Integer destOfferGroupId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "ORI_LOWER_LIMIT")
  private Integer oriLowerLimit;

  @Column(name = "ORI_UPPER_LIMIT")
  private Integer oriUpperLimits;

}
