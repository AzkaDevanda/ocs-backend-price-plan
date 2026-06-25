package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Table(name = "SUBS_UPP_INST", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsUppInst {

  @Id
  @Column(name = "SUBS_UPP_INST_ID", nullable = false)
  private Long subsUppInstId;

  @Column(name = "SUBS_ID", nullable = false, updatable = false)
  private Long subsId;

  @Column(name = "PRICE_PLAN_ID", nullable = false)
  private Long pricePlanId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "PROD_ID")
  private Long prodId;

  @Column(name = "PARENT_SUBS_UPP_INST_ID")
  private Long parentSubsUppInstId;

  @Column(name = "COMPLETED_DATE", nullable = false)
  private LocalDateTime completedDate;

  @Column(name = "NEED_UPLOAD", length = 1)
  @Builder.Default
  private String needUpload = "N";

  @Column(name = "PACKAGE_FLAG", length = 1)
  private String packageFlag;

  @Column(name = "AGM_EFF_DATE")
  private LocalDateTime agmEffDate;

  @Column(name = "AGM_EXP_DATE")
  private LocalDateTime agmExpDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "UPLOAD_TYPE", length = 1)
  private String uploadType;

  @Column(name = "PROD_ALIAS", length = 30)
  private String prodAlias;

  @Column(name = "PARENT_OFFER_ID")
  private Long parentOfferId;

  @Column(name = "AGREEMENT_TIME_UNIT", length = 1)
  private String agreementTimeUnit;

  @Column(name = "AGREEMENT_LIMIT")
  private Long agreementLimit;

  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();

    // 1. Audit Dates (Wajib Not Null)
    if (this.createdDate == null) {
      this.createdDate = now;
    }

    if (this.updateDate == null) {
      this.updateDate = now;
    }

    if (this.completedDate == null) {
      this.completedDate = now;
    }

    // 2. State & State Date (Wajib Not Null)
    if (this.state == null) {
      this.state = "A"; // Default 'A' untuk Active
    }

    if (this.stateDate == null) {
      this.stateDate = now;
    }

    // 3. Effective Date (Wajib Not Null)
    if (this.effDate == null) {
      this.effDate = now;
    }

    // 4. Default Flags
    if (this.packageFlag == null) {
      this.packageFlag = "N";
    }

    if (this.needUpload == null) {
      this.needUpload = "N";
    }
  }
}
