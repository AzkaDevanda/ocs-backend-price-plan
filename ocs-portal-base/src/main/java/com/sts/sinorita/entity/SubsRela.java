package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SUBS_RELA", schema = "STS", indexes = {
    @Index(name = "IDX_SUBS_RELA_PARENT_ID", columnList = "PARENT_SUBS_ID"),
    @Index(name = "IDX_SUBS_RELA_SUBS_ID", columnList = "SUBS_ID")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubsRela {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subs_rela_id_seq")
  @SequenceGenerator(name = "subs_rela_id_seq", sequenceName = "SUBS_RELA_ID_SEQ", allocationSize = 1)
  @Column(name = "SUBS_RELA_ID", nullable = false, precision = 12, scale = 0)
  private Long subsRelaId;

  // =========================
  // COLUMNS
  // =========================
  @Column(name = "SUBS_ID", nullable = false, precision = 12, scale = 0)
  private Long subsId;

  @Column(name = "BIND_TYPE", nullable = false, length = 3)
  private String bindType;

  @Column(name = "PARENT_SUBS_ID", nullable = false, precision = 12, scale = 0)
  private Long parentSubsId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "RELA_EFF_DATE")
  private LocalDateTime relaEffDate;

  @Column(name = "RELA_EXP_DATE")
  private LocalDateTime relaExpDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}
