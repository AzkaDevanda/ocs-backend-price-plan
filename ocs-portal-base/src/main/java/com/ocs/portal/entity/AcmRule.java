package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACM_RULE")
@SequenceGenerator(name = "ACM_RULE_ID_SEQ_generator", sequenceName = "ACM_RULE_ID_SEQ", allocationSize = 1)
public class AcmRule implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACM_RULE_ID_SEQ_generator")
  @Column(name = "ACM_RULE_ID", nullable = false)
  private Integer id;

  @Column(name = "PRICE_VER_ID", nullable = false)
  private Integer priceVerId;

  @Lob
  @Column(name = "RULE_SCRIPT", nullable = false)
  private String ruleScript;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "RULE_COMMENTS", length = 4000)
  private String ruleComments;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "SCRIPT_PAGE")
  private byte[] scriptPage;

  @Column(name = "SCRIPT_TEMPLET_ID")
  private Integer scriptTempletId;
}
