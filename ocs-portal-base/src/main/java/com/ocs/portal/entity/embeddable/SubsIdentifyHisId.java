package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsIdentifyHisId implements Serializable {

  @Column(name = "SUBS_IDENTIFY_ID", nullable = false)
  private Long subsIdentifyId;

  @Column(name = "SEQ", nullable = false)
  private Long seq;
}
