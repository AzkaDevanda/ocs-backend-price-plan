package com.ocs.portal.entity.embeddable;

import lombok.Data;

import java.io.Serializable;

@Data
public class DomainId implements Serializable {
  private String tableName;
  private String columnName;
  private String value;
}