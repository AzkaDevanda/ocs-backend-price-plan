package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUBS_PLAN_OFFER_ATTR", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsPlanOfferAttr {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBS_PLAN_OFFER_ATTR_ID_SEQ_GEN")
  @SequenceGenerator(name = "SUBS_PLAN_OFFER_ATTR_ID_SEQ_GEN", sequenceName = "SUBS_PLAN_OFFER_ATTR_ID_SEQ", allocationSize = 1)
  @Column(name = "SUBS_PLAN_OFFER_ATTR_ID", nullable = false)
  private Long subsPlanOfferAttrId;

  @Column(name = "OFFER_VER_ID", nullable = false)
  private Long offerVerId;

  @Column(name = "OFFER_ID")
  private Long offerId;

  @Column(name = "ATTR_ID")
  private Long attrId;

  @Column(name = "DEFAULT_VALUE", length = 255)
  private String defaultValue;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "EXCLUDE_FLAG", length = 1)
  private String excludeFlag;

  @Column(name = "MASK", length = 255)
  private String mask;

  @Column(name = "EXCEPTION_MESSAGE", length = 60)
  private String exceptionMessage;
}
