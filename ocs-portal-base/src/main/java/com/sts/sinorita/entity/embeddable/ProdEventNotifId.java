package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ProdEventNotifId implements Serializable {
  @Column(name = "NODE_ID", nullable = false)
  private Long nodeId;

  @Column(name = "ADVICE_TYPE", nullable = false)
  private Long adviceType;
}
