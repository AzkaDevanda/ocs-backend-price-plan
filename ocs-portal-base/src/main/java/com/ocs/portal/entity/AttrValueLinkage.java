package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AttrValueLinkageId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATTR_VALUE_LINKAGE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttrValueLinkage {
  @EmbeddedId
  private AttrValueLinkageId id;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "DEFAULT_FLAG", length = 1)
  private String defaultFlag;
}
