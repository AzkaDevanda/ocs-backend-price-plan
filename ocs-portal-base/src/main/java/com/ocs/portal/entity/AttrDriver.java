package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATTR_DRIVER", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttrDriver {
  @Id
  @Column(name = "ATTR_DRIVER_ID", nullable = false)
  private Long attrDriverId;

  @Column(name = "ORG_ATTR_ID", nullable = false)
  private Long orgAttrId;

  @Column(name = "ORG_ATTR_VALUE_ID")
  private Long orgAttrValueId;

  @Column(name = "OBJ_ATTR_ID", nullable = false)
  private Long objAttrId;

  @Column(name = "SP_ID")
  private Long spId;
}