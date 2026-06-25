package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.SubsAcmId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SUBS_ACM", schema = "MDBTT")
public class SubsAcm {
  @EmbeddedId
  private SubsAcmId id;

  @Column(name = "VALUE")
  private Long value;

}