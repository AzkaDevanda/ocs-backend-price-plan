package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SUBS_ACM_PROD", schema = "MDBTT")
public class SubsAcmProd {
  @EmbeddedId
  private SubsAcmProdId id;

  @Column(name = "VALUE")
  private Long value;

}