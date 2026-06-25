package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

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
public class ReserveBalId implements Serializable {

  @Column(name = "SESSION_ID", nullable = false, length = 255)
  private String sessionId;

  @Column(name = "BAL_ID", nullable = false)
  private Long balId;
}