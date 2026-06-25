package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HLR")
public class Hlr {
  @Id
  @Column(name = "HLR_ID", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "AREA_ID", nullable = false)
  private Integer areaId;

  @Size(max = 60)
  @Column(name = "BEGIN_ACC_NBR", length = 60)
  private String beginAccNbr;

  @Size(max = 60)
  @Column(name = "END_ACC_NBR", length = 60)
  private String endAccNbr;

  @Size(max = 30)
  @Column(name = "HLR_TYPE", length = 30)
  private String hlrType;

  @Size(max = 30)
  @Column(name = "HLR_EDITION", length = 30)
  private String hlrEdition;

  @Size(max = 60)
  @NotNull
  @Column(name = "HLR_NAME", nullable = false, length = 60)
  private String hlrName;

  @Column(name = "IS_ONLINE")
  private Boolean isOnline;

  @Column(name = "IS_LOGIC_FLAG")
  private Boolean isLogicFlag;

  @Column(name = "PHS_HLR_ID")
  private Integer phsHlrId;

  @Column(name = "SP_ID")
  private Integer spId;

}