package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SubsPlanOfferSelectId implements Serializable {
  @Column(name = "OFFER_VER_ID", nullable = false)
  private Long offerVerId;

  @Column(name = "OFFER_GROUP_ID", nullable = false)
  private Long offerGroupId;
}
