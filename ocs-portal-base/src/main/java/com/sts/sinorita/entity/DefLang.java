package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DEF_LANG", schema = "STS")
public class DefLang {
  @Id
  @Column(name = "DEF_LANG_ID", nullable = false)
  private Long defLangId;

  @Column(name = "STD_CODE", nullable = false, length = 60)
  private String stdCode;

  @Column(name = "DEF_LANG_NAME", nullable = false, length = 60)
  private String defLangName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "I18N_CODE", length = 30)
  private String i18nCode;
}
