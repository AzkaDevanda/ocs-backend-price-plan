package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BFM_DATA_PRIV", schema = "POT")
public class BfmDataPriv {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_bfm_dir_id_seq")
  @SequenceGenerator(name = "t_bfm_dir_id_seq", sequenceName = "T_BFM_DIR_ID_SEQ", allocationSize = 1)
  @Column(name = "DATA_PRIV_ID", nullable = false)
  private Long id;

  @Size(max = 60)
  @NotNull
  @Column(name = "DATA_PRIV_NAME", nullable = false, length = 60)
  private String dataPrivName;

  @Size(max = 60)
  @NotNull
  @Column(name = "DATA_PRIV_CODE", nullable = false, length = 60)
  private String dataPrivCode;

  @Size(max = 255)
  @Column(name = "COMMENTS")
  private String comments;

  @NotNull
  @Column(name = "DATA_TYPE")
  private Boolean dataType;

  @Size(max = 4000)
  @Column(name = "DATA_SRC", length = 4000)
  private String dataSrc;

  @Column(name = "SP_ID")
  private Integer spId;

  @Size(max = 16)
  @Column(name = "MODULE", length = 16)
  private String module;

}