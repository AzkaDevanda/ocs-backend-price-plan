package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FellowNbrId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "SUBS_ID", nullable = false, precision = 12, scale = 0)
  private Long subsId;

  @Column(name = "SEQ", nullable = false, precision = 9, scale = 0)
  private Long seq;
}
