package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "OBJ_ATTR", schema = "STS")
@NoArgsConstructor
@AllArgsConstructor
public class ObjAttr {
  @Id
  @Column(name = "OBJ_ATTR_ID", nullable = false)
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "obj_attr_seq_generator")
  // @SequenceGenerator(name = "obj_attr_seq_generator", sequenceName = "OBJ_ATTR_ID_SEQ", allocationSize = 1)
  private Integer objAttrId;

  @Column(name = "SP_ID")
  private Integer spId;
}
