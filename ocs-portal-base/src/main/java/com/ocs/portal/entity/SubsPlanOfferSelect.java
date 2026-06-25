package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.SubsPlanOfferSelectId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUBS_PLAN_OFFER_SELECT", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsPlanOfferSelect {
  @EmbeddedId
  private SubsPlanOfferSelectId id;

  @Column(name = "NECESSARY", nullable = false, length = 1)
  private String necessary;

  @Column(name = "SEQ")
  private Long seq;

  @Column(name = "SP_ID")
  private Long spId;
}
