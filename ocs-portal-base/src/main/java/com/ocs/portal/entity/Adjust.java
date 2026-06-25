package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "ADJUST")
public class Adjust {
  @Id
  @NotNull
  @Column(name = "ADJUST_ID", nullable = false)
  private Long adjustId;

  @Column(name = "ADJUST_REASON_ID")
  private Short adjustReasonId;

  @Size(max = 4000)
  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "REVERSED_ADJUST_ID")
  private Long reversedAdjustId;

  @Column(name = "SP_ID")
  private Integer spId;

  @NotNull
  @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
  @Column(name = "PART_ID", nullable = false)
  private Short partId;

  @Column(name = "REVERSED_BY_ADJUST_ID")
  private Long reversedByAdjustId;

  @Column(name = "ADJUST_OBJ_TYPE")
  private Boolean adjustObjType;

  @Column(name = "SUBS_ID")
  private Long subsId;

}