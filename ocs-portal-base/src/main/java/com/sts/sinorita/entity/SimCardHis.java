package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.sts.sinorita.entity.embeddable.SimCardHisId;

@Entity
@Table(name = "SIM_CARD_HIS", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimCardHis {

  @EmbeddedId
  private SimCardHisId id;

  @Column(name = "SIM_TYPE_ID", nullable = false)
  private Integer simTypeId;

  @Column(name = "ICCID", nullable = false, length = 60)
  private String iccid;

  @Column(name = "HLR_ID")
  private Long hlrId;

  @Column(name = "IMSI", nullable = false, length = 60)
  private String imsi;

  @Column(name = "PIN1", length = 120)
  private String pin1;

  @Column(name = "PUK1", length = 120)
  private String puk1;

  @Column(name = "PIN2", length = 120)
  private String pin2;

  @Column(name = "PUK2", length = 120)
  private String puk2;

  @Column(name = "KI", length = 120)
  private String ki;

  @Column(name = "STAFF_ID")
  private Long staffId;

  @Column(name = "ORG_ID")
  private Long orgId;

  @Column(name = "SIM_STATE", nullable = false, length = 1)
  private String simState;

  @Column(name = "AREA_ID")
  private Long areaId;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "IMSI2", length = 60)
  private String imsi2;

  @Column(name = "KI2", length = 120)
  private String ki2;

  @Column(name = "ESN", length = 60)
  private String esn;

  @Column(name = "NAI_USERNAME", length = 120)
  private String naiUsername;

  @Column(name = "NAI_PASSWORD", length = 120)
  private String naiPassword;

  @Column(name = "INJECT_FLAG", length = 1)
  private String injectFlag;

  @Column(name = "ADM", length = 60)
  private String adm;

  @Column(name = "RECYCLE_FLAG", length = 1)
  private String recycleFlag;

  @Column(name = "CHECK_SUM", length = 120)
  private String checkSum;

  @Column(name = "K4", length = 60)
  private String k4;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 30)
  private String partyCode;

  @Column(name = "MODI_DATE")
  private LocalDate modiDate;

  @Column(name = "SIM_CARD_ATTR", length = 1000)
  private String simCardAttr;

  @Column(name = "SP_ID")
  private Long spId;

  /** Oracle default SYSDATE, kita handle di JPA */
  @PrePersist
  public void applyDefaults() {
    if (this.createdDate == null) {
      this.createdDate = LocalDate.now();
    }
  }
}
