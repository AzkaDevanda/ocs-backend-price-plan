package com.ocs.portal.entity.embeddable;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcctAttrValueId implements Serializable {
  @Column(name = "ACCT_ID", nullable = false)
  private Long acctId;

  @Column(name = "ATTR_ID", nullable = false)
  private Long attrId;
}