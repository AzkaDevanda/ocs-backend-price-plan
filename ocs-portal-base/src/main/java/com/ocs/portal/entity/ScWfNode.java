package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SC_WF_NODE", schema = "STS")
public class ScWfNode {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_wf_node_id_seq")
  @SequenceGenerator(name = "sc_wf_node_id_seq", sequenceName = "SC_WF_NODE_ID_SEQ", allocationSize = 1)
  @Column(name = "WF_NODE_ID")
  private Long wfNodeId;

  @Column(name = "WF_TEMPLET_ID")
  private Long wfTempletId;

  @Column(name = "PARENT_WF_NODE_ID")
  private Long parentWfNodeId;

  @Column(name = "ADVICE_TYPE")
  private Long adviceType;

  @Column(name = "WF_NODE_NAME", length = 60)
  private String wfNodeName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "INPUT_CMD", length = 1000)
  private String inputCmd;

  @Column(name = "TIME_LIMIT")
  private Long timeLimit;

  @Column(name = "INPUT_PARAM", length = 4000)
  private String inputParam;

  @Column(name = "AFTER_CMD", length = 30)
  private String afterCmd;

  @Column(name = "ERROR_ADVICE_TYPE")
  private Long errorAdviceType;

  @Column(name = "RETURN_NODE_QTY")
  private Integer returnNodeQty;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ERROR_BUSI_EX", length = 60)
  private String errorBusiEx;
}
