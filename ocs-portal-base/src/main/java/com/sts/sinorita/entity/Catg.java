package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATG", schema = "STS")
public class Catg implements Serializable {

  @Id
  @Column(name = "CATG_ID", nullable = false)
  private Integer catgId;

  @Column(name = "CATG_TYPE", nullable = false, length = 1)
  private String catgType;

  @Column(name = "CATG_DEF_TYPE", nullable = false, length = 1)
  private String catgDefType;

  @Column(name = "CATG_NAME", nullable = false, length = 60)
  private String catgName;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "SP_ID")
  private Integer spId;
}
