package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUNDLE_UNIT", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BundleUnit {
  @Id
  @Column(name = "BUNDLE_UNIT_ID", nullable = false)
  private Long bundleUnitId;

  @Column(name = "BUNDLE_COMPOSE_TYPE", nullable = false, length = 1)
  private String bundleComposeType;

  @Column(name = "OFFER_VER_ID")
  private Long offerVerId;

  @Column(name = "UPPER_LIMIT")
  private Integer upperLimit;

  @Column(name = "LOWER_LIMIT")
  private Integer lowerLimit;

  @Column(name = "NECESSARY", nullable = false, length = 1)
  private String necessary;

  @Column(name = "AGREEMENT_PERIOD")
  private Integer agreementPeriod;

  @Column(name = "TIME_UNIT", length = 1)
  private String timeUnit;

  @Column(name = "MEMBER_ALIAS", length = 30)
  private String memberAlias;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "AGREEMENT_EFF_TYPE", length = 1)
  private String agreementEffType;

  @Column(name = "SEQ", length = 6)
  private String seq;

  @Column(name = "CUST_SOURCE_TYPE", length = 1)
  private String custSourceType;
}
