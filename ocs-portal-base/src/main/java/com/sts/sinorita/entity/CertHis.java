package com.sts.sinorita.entity;

import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.CertHisId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CERT_HIS", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertHis {
  @EmbeddedId
  private CertHisId id;

  @Column(name = "CERT_TYPE_ID", nullable = false)
  private Integer certTypeId;

  @Column(name = "CERT_NBR", nullable = false, length = 120)
  private String certNbr;

  @Column(name = "ISSUE_ORG", length = 120)
  private String issueOrg;

  @Column(name = "ISSUE_DATE")
  private LocalDateTime issueDate;

  @Column(name = "EFF_DATE")
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CERT_ADDRESS", length = 255)
  private String certAddress;

  @Lob
  @Column(name = "EXT_ATTR")
  private String extAttr;

  @Column(name = "REC_CREATED_DATE")
  private LocalDateTime recCreatedDate;

  @Column(name = "ISSUE_COUNTRY")
  private Long issueCountry;

}
