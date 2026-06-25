package com.ocs.portal.entity.pot;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BFM_PORTAL_CHANNEL", schema = "POT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BfmPortalChannel {
  @Id
  @Column(name = "PORTAL_ID", nullable = false)
  private Long portalId;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Long contactChannelId;
}
