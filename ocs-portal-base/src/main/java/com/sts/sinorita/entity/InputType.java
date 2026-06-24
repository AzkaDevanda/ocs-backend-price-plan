package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INPUT_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputType {
  @Id
  @Column(name = "INPUT_TYPE", nullable = false)
  private Character inputType;

  @Column(name = "INPUT_TYPE_NAME", nullable = false, length = 60)
  private String inputTypeName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;
}
