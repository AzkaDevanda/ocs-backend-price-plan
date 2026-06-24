package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalShareOperationHisId {

  @Column(name = "BAL_SHARE_ID")
  private Long balShareId;

  @Column(name = "SEQ")
  private Long seq;

}
