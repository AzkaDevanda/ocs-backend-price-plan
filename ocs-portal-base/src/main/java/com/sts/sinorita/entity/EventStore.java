package com.sts.sinorita.entity;

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
@Table(name = "EVENT_STORE")
public class EventStore {
  @Id
  @Column(name = "RE_ID", nullable = false)
  private Integer id;

  @Size(max = 60)
  @NotNull
  @Column(name = "BASE_TABLE_NAME", nullable = false, length = 60)
  private String baseTableName;

  @Column(name = "SP_ID")
  private Integer spId;

}