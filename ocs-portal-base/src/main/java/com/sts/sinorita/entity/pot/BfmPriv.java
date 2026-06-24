package com.sts.sinorita.entity.pot;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_PRIV", schema = "POT")
public class BfmPriv {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_bfm_priv_id_seq")
  @SequenceGenerator(name = "t_bfm_priv_id_seq", sequenceName = "T_BFM_PRIV_ID_SEQ", allocationSize = 1)
  @Column(name = "PRIV_ID", nullable = false)
  private Long privId;

  @Column(name = "PRIV_TYPE", length = 1)
  private String privType;

  @Column(name = "PRIV_CODE", length = 60)
  private String privCode;

  @Column(name = "PRIV_NAME", length = 60, nullable = false)
  private String privName;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "URL", length = 4000)
  private String url;

  @Column(name = "STATE", length = 1)
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "PRIV_EL", length = 4000)
  private String privEl;

  @Column(name = "CDN_URL", length = 255)
  private String cdnUrl;

  @Column(name = "JS_FILE", length = 255)
  private String jsFile;

  @Column(name = "CSS_FILE", length = 255)
  private String cssFile;

  @Column(name = "IS_HOLD", length = 1)
  private String isHold;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "APP_ID")
  private Long appId;
}