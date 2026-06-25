package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class AdviceTypeCronId implements Serializable {
  @Column(name = "ADVICE_TYPE", nullable = false)
  private Long adviceType;

  @Column(name = "PRIORITY", nullable = false, length = 1)
  private String priority;
}
