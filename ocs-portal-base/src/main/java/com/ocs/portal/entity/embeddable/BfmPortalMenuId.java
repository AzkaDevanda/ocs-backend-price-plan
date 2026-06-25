package com.ocs.portal.entity.embeddable;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class BfmPortalMenuId implements Serializable {
  @Column(name = "PORTAL_ID")
  private Long portalId;

  @Column(name = "PARTY_ID")
  private Long partyId;

  @Column(name = "SEQ")
  private Long seq;
}
