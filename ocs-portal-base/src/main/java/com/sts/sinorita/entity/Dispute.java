package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DISPUTE")
public class Dispute {
  @Id
  @Column(name = "DISPUTE_ID", nullable = false)
  private Long id;

  @Size(max = 4000)
  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "SP_ID")
  private Integer spId;

}