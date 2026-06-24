package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SC_WF_NODE_BC", schema = "STS")
public class ScWfNodeBc {
  @Id
  @Column(name = "BC_ID")
  private Long bcId;

  @Column(name = "WF_NODE_ID")
  private Long wfNodeId;

  @Column(name = "SEQ")
  private Integer seq;

  @Column(name = "SP_ID")
  private Long spId;
}
