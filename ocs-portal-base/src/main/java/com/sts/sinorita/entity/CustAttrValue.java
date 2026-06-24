package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.sts.sinorita.entity.embeddable.CustAttrValueId;

@Entity
@Table(name = "CUST_ATTR_VALUE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustAttrValue {
  @EmbeddedId
  private CustAttrValueId id;

  @Column(name = "ATTR_VALUE", length = 255)
  private String attrValue;

  @Column(name = "NEED_UPLOAD", length = 1)
  @Builder.Default
  private String needUpload = "N";

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "EFF_DATE")
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "UPDATE_DATE")
  private LocalDate updateDate;
}
