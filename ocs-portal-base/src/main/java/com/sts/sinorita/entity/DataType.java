package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DATA_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataType {
  @Id
  @Column(name = "DATA_TYPE", nullable = false)
  private Character dataType;

  @Column(name = "DATA_TYPE_NAME", nullable = false, length = 60)
  private String dataTypeName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;
}
