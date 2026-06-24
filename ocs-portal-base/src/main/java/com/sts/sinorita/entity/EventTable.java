package com.sts.sinorita.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EVENT_TABLE")
public class EventTable {

  @Id
  @Column(name = "EVENT_TABLE_ID")
  private Integer id;

  @Column(name = "EVENT_TABLE_NAME", length = 60)
  private String eventTableName;

  @Column(name = "BASE_TABLE_NAME", length = 60)
  private String baseTableName;

  @Column(name = "BEGIN_DATE")
  private LocalDateTime beginDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "RECORD_COUNT")
  private Long recordCount;

  @Column(name = "STATE")
  private Character state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "CREATE_DATE")
  private LocalDateTime createDate;

  @Column(name = "SP_ID")
  private Long spId;

}
