package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.GstTypeTaxRateId;

@Entity
@Table(name = "GST_TYPE_TAX_RATE", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstTypeTaxRate {
  @EmbeddedId
  private GstTypeTaxRateId id;

  @Column(name = "TAX_RATE", nullable = false)
  private Integer taxRate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "GST_SEQ")
  private Integer gstSeq;

  @Column(name = "SP_ID")
  private Long spId;
}