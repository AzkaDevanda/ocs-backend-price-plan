package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbCancelId implements Serializable {
  @Column(name = "ACCT_BOOK_ID", nullable = false)
  private Long acctBookId;

  @Column(name = "BAL_ID", nullable = false)
  private Long balId;
}