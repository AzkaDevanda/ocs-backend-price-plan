package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "WHOLESALE", schema = "STS")
public class Wholesale implements Serializable {

  @Id
  @Column(name = "WHOLESALE_ID", nullable = false)
  private Long wholesaleId;

  @Column(name = "ORG_ID")
  private Long orgId;

  @Column(name = "SUBS_EVENT_ID")
  private Long subsEventId;

  @Column(name = "REQ_DATE")
  private LocalDateTime reqDate;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "COMMISION_AMOUNT")
  private Long commisionAmount;

  @Column(name = "WHOLESALE_CODE", length = 30)
  private String wholesaleCode;

  @Column(name = "INVOICE_NO", length = 60)
  private String invoiceNo;

  @Column(name = "PREFIX", length = 60)
  private String prefix;

  @Column(name = "START_NBR", length = 60)
  private String startNbr;

  @Column(name = "END_NBR", length = 60)
  private String endNbr;

  @Column(name = "OFFER_ID")
  private Long offerId;

  @Column(name = "SUBS_PLAN_ID")
  private Long subsPlanId;

  @Column(name = "PARTY_TYPE", length = 1, nullable = false)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @Column(name = "PRIORITY")
  private Long priority;

  @Column(name = "STATE", length = 1, nullable = false)
  private String state;

  @Lob
  @Column(name = "EXT_ATTR")
  private byte[] extAttr;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "CUST_ORDER_ID")
  private Long custOrderId;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Long contactChannelId;

}
