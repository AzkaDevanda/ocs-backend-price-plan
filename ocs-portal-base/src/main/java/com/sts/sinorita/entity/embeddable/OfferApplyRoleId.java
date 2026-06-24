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
public class OfferApplyRoleId implements Serializable {

  @Column(name = "ROLE_ID", nullable = false)
  private Integer roleId;

  @Column(name = "OFFER_ID", nullable = false)
  private Integer offerId;
}
