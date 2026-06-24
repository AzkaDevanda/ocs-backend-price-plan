package com.sts.sinorita.entity.mdbtt;

import com.sts.sinorita.entity.embeddable.AcctAcmCycleId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCT_ACM_CYCLE", schema = "MDBTT")
public class AcctAcmCycle {
  @EmbeddedId
  private AcctAcmCycleId id;

  @Column(name = "VALUE")
  private Long value;

}