package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class AdviceTypeLangId implements Serializable {

  @Column(name = "ADVICE_TYPE", nullable = false)
  private Long adviceType;

  @Column(name = "DEF_LANG_ID", nullable = false)
  private Long defLangId;
}
