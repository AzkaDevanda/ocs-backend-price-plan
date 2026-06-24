package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

import com.sts.sinorita.entity.embeddable.AcctAttrValueId;

@Entity
@Table(name = "ACCT_ATTR_VALUE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcctAttrValue implements Serializable {

  @EmbeddedId
  private AcctAttrValueId id;

  @Column(name = "ATTR_VALUE", length = 120)
  private String attrValue;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EFF_DATE")
  private Date effDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EXP_DATE")
  private Date expDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UPDATE_DATE", nullable = false)
  private Date updateDate;

  @Builder.Default
  @Column(name = "NEED_UPLOAD", length = 1)
  private String needUpload = "N";

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;
}
