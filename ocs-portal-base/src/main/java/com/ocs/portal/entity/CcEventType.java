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
@Table(name = "CC_EVENT_TYPE")
public class CcEventType {
  @Id
  @Column(name = "EVENT_TYPE", nullable = false)
  private Integer id;

  @Size(max = 60)
  @NotNull
  @Column(name = "EVENT_TYPE_NAME", nullable = false, length = 60)
  private String eventTypeName;

  @Size(max = 3000)
  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Size(max = 60)
  @NotNull
  @Column(name = "EVENT_TYPE_CODE", nullable = false, length = 60)
  private String eventTypeCode;

}