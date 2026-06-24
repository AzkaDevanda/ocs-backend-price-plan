package com.sts.sinorita.entity.pot;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BFM_PORTAL", schema = "POT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BfmPortal {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_bfm_portal_id_seq")
  @SequenceGenerator(name = "t_bfm_portal_id_seq", sequenceName = "T_BFM_PORTAL_ID_SEQ", allocationSize = 1)
  @Column(name = "PORTAL_ID", nullable = false)
  private Long portalId;

  @Column(name = "PORTAL_NAME", length = 255)
  private String portalName;

  @Column(name = "URL", length = 255)
  private String url;

  @Column(name = "EXTRA_URL", length = 255)
  private String extraUrl;

  @Column(name = "ICON_URL", length = 255)
  private String iconUrl;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "APP_ID")
  private Long appId;

  @Column(name = "INDEX_ID")
  private Long indexId;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ALLOW_EXTERNAL_ACCESS", length = 1)
  private String allowExternalAccess;
}
