package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ADVICE_MACRO_SCRIPT", schema = "STS", uniqueConstraints = {
    @UniqueConstraint(name = "AK_MARCO_SCRIPT_VALUE_SCRIPT", columnNames = { "VALUE_SCRIPT", "SP_ID" })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceMacroScript implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "MACRO_SCRIPT_ID", nullable = false, precision = 6, scale = 0)
  private Long macroScriptId;

  @Column(name = "SCRIPT_NAME", nullable = false, length = 255)
  private String scriptName;

  @Column(name = "VALUE_SCRIPT", nullable = false, length = 4000)
  private String valueScript;

  /**
   * S = SQL
   * B = Binary / Script
   */
  @Column(name = "SCRIPT_TYPE", nullable = false, length = 1)
  private String scriptType;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "PARAM_SET", length = 255)
  private String paramSet;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  /**
   * C = Customer profile
   * S = Subscriber profile
   * O = Order
   * P = Public
   */
  @Column(name = "MACRO_CATG", nullable = false, length = 1)
  private String macroCatg;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}
