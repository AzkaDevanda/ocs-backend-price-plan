package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OfferApplyCatgId implements Serializable {

  @Column(name = "CATG_ID", nullable = false)
  private Integer catgId;

  @Column(name = "OFFER_ID", nullable = false)
  private Integer offerId;
}
