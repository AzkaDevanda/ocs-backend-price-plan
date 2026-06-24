package com.sts.sinorita.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.DpOfferOrderAttrId;

@Entity
@Table(name = "DP_OFFER_ORDER_ATTR", schema = "STS")
@IdClass(DpOfferOrderAttrId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DpOfferOrderAttr {

  @Id
  @Column(name = "DP_ORDER_ID", nullable = false)
  private Long dpOrderId;

  @Id
  @Column(name = "ATTR_ID", nullable = false)
  private Long attrId;

  @Column(name = "VALUE", length = 255)
  private String value;

  @Column(name = "OLD_VALUE", length = 255)
  private String oldValue;

  @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
  private String operationType;

  @Column(name = "EFF_DATE")
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "PART_ID", nullable = false, updatable = false)
  private Integer partId;

  @PrePersist
  public void prePersist() {

    if (partId == null) {
      partId =  LocalDate.now().getMonthValue();
    }
  }

  @Transient
  private String valueMark;
  @Transient
  private Long offerId;
  @Transient
  private String offerSeq;
  @Transient
  private String attrName;
  @Transient
  private String oldValueMark;
}