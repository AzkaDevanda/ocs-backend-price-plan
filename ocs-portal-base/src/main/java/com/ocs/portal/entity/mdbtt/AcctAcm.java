package com.ocs.portal.entity.mdbtt;

import com.ocs.portal.entity.embeddable.AcctAcmId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCT_ACM", schema = "MDBTT")
public class AcctAcm {
  @EmbeddedId
  private AcctAcmId id;

  @Column(name = "VALUE")
  private Long value;

}