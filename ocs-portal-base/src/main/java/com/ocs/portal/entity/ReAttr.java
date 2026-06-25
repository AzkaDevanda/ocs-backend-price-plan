package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RE_ATTR")
public class ReAttr implements Serializable {
  @Id
  @Column(name = "RE_ATTR", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "re_attr_seq_generator")
  @SequenceGenerator(name = "re_attr_seq_generator", sequenceName = "RE_ATTR_SEQ", allocationSize = 1)
  private Integer id;

  @Column(name = "RE_ATTR_SRC_TYPE", nullable = false)
  private Character reAttrSrcType;

  @Column(name = "RE_TYPE")
  private Character reType;

  @Column(name = "MEASURABLE", nullable = false)
  private Character measurable;

  @Column(name = "RE_ATTR_NAME", nullable = false, length = 60)
  private String reAttrName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "SP_ID")
  private Integer spId;
}