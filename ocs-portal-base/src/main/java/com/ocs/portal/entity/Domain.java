package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.DomainId;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(DomainId.class)
@Table(name = "DOMAIN", schema = "STS")
public class Domain {
  @Id
  @Column(name = "TABLE_NAME", nullable = false)
  private String tableName;

  @Id
  @Column(name = "COLUMN_NAME", nullable = false)
  private String columnName;

  @Id
  @Column(name = "VALUE", nullable = false)
  private String value;

  @Column(name = "LOOKUP_NAME", nullable = false)
  private String lookupName;

  @Column(name = "COMMENTS")
  private String comments;
}
