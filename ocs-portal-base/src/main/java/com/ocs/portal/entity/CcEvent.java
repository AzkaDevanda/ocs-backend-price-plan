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

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CC_EVENT")
public class CcEvent {
  @Id
  @NotNull
  @Column(name = "EVENT_ID", nullable = false)
  private Long eventId;

  @NotNull
  @Column(name = "EVENT_FORMAT_ID", nullable = false)
  private Integer eventFormatId;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @Size(max = 60)
  @Column(name = "PREFIX", length = 60)
  private String prefix;

  @Size(max = 60)
  @Column(name = "ACC_NBR", length = 60)
  private String accNbr;

  @NotNull
  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @NotNull
  @Column(name = "STATE", nullable = false)
  private Character state;

  @NotNull
  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Size(max = 4000)
  @Column(name = "EVENT_PARAM", length = 4000)
  private String eventParam;

  @Size(max = 4000)
  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "SP_ID")
  private Integer spId;

  @ColumnDefault("to_number(to_char(sysdate, 'DD'))")
  @Column(name = "PART_ID")
  private Integer partId;

  @Size(max = 4000)
  @Column(name = "EVENT_PARAM1", length = 4000)
  private String eventParam1;

}