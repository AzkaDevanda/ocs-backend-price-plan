package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ADVICE_MACRO", schema = "STS", uniqueConstraints = {
    @UniqueConstraint(name = "AK_ADVICE_MARCO_CODE", columnNames = { "MACRO_CODE", "SP_ID" })
})
public class AdviceMacro {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advice_macro_id_seq")
  @SequenceGenerator(name = "advice_macro_id_seq", sequenceName = "ADVICE_MACRO_ID_SEQ", allocationSize = 1)
  @Column(name = "MACRO_ID", nullable = false)
  private Long macroId;

  @Column(name = "MACRO_SCRIPT_ID")
  private Long macroScriptId; // FK tapi kita buat sebagai kolom biasa tanpa relasi

  @Column(name = "MACRO_CODE", nullable = false, length = 30)
  private String macroCode;

  @Column(name = "MACRO_NAME", nullable = false, length = 60)
  private String macroName;

  @Column(name = "MACRO_KEY", length = 60)
  private String macroKey;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;
}
