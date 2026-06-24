package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ADVICE_SENDER_INFO", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdviceSenderInfo {
  @Id
  @Column(name = "ADVICE_ID", nullable = false)
  private Long adviceId;

  @Column(name = "SENDED_DATE")
  private LocalDate sendedDate;

  @Column(name = "PARTY_TYPE", nullable = false, length = 30)
  private String partyType;

  @Column(name = "PARTY_CODE", nullable = false, length = 30)
  private String partyCode;

  @Column(name = "PART_ID", nullable = false)
  private Integer partId;

  @Column(name = "SP_ID")
  private Long spId;

  /**
   * Default PART_ID = bulan sekarang (1–12)
   * mengikuti default Oracle: to_number(to_char(sysdate,'mm'))
   */
  @PrePersist
  public void applyDefaults() {
    if (this.partId == null) {
      this.partId = LocalDate.now().getMonthValue();
    }
  }
}