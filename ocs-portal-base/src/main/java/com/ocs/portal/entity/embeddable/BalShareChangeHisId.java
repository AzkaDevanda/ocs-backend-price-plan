package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BalShareChangeHisId {

  @Column(name = "BAL_SHARE_ID")
  private Long balShareId;

  @Column(name = "SEQ")
  private Long seq;

}
