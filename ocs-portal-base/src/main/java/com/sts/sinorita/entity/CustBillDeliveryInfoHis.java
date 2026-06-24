package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.CustBillDeliveryInfoHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUST_BILL_DELIVERY_INFO_HIS", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustBillDeliveryInfoHis {

  @EmbeddedId
  private CustBillDeliveryInfoHisId id;

  @Column(name = "CUST_ID", nullable = false)
  private Long custId;

  @Column(name = "STD_ADDR_ID")
  private Long stdAddrId;

  @Column(name = "DETAIL_INFO", length = 4000)
  private String detailInfo;

  @Column(name = "FILE_TYPE", length = 1)
  private String fileType;

  @Column(name = "ZIPCODE", length = 255)
  private String zipcode;

  @Column(name = "EMAIL", length = 255)
  private String email;

  @Column(name = "CC_EMAIL", length = 255)
  private String ccEmail;

  @Column(name = "CREATED_DATE", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdDate;

  @Column(name = "SMS_NBR", length = 60)
  private String smsNbr;

  @Column(name = "FAX_NBR", length = 60)
  private String faxNbr;

  @Column(name = "REC_EFF_DATE", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime recEffDate;

  @Column(name = "REC_EXP_DATE", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime recExpDate;

  @Column(name = "STATE", length = 1)
  private String state;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;

  @Lob
  @Column(name = "EXT_ATTR")
  private String extAttr;

  @Column(name = "REC_CREATED_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime recCreatedDate;

}
