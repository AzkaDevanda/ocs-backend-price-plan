package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSpanDetailId implements Serializable {
  @Column(name = "TIME_SPAN_ID", nullable = false)
  private Long timeSpanId;

  @Column(name = "SEQ", nullable = false)
  private Long seq;
}
