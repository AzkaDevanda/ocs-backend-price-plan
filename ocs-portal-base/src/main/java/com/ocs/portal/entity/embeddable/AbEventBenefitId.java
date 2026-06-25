package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AbEventBenefitId implements Serializable {

//    private Long eventInstId;
//    private Long acctBookId;

    //  @NotNull
  @Column(name = "EVENT_INST_ID", nullable = false)
  private Long eventInstId;

  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;
}
