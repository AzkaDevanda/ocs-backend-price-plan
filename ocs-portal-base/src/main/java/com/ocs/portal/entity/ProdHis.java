package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.ocs.portal.entity.embeddable.ProdHisId;

@Entity
@Table(name = "PROD_HIS", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdHis {
  @EmbeddedId
  private ProdHisId id;

  @Column(name = "OFFER_ID", nullable = false)
  private Long offerId;

  @Column(name = "SUBS_PLAN_ID")
  private Long subsPlanId;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "COMPLETED_DATE", nullable = false)
  private LocalDate completedDate;

  @Column(name = "PROD_STATE", nullable = false, length = 1)
  private String prodState;

  @Column(name = "PROD_STATE_DATE", nullable = false)
  private LocalDate prodStateDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDate updateDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "BLOCK_REASON", length = 60)
  private String blockReason;

  @Column(name = "PROD_EXP_DATE")
  private LocalDate prodExpDate;

  @Column(name = "PACKAGE_FLAG", length = 1)
  private String packageFlag;

  @Column(name = "PARENT_PROD_ID")
  private Long parentProdId;

  @Column(name = "INDEP_PROD_ID")
  private Long indepProdId;

  @Column(name = "NEED_UPLOAD", length = 1)
  private String needUpload;

  @Column(name = "AGREEMENT_EXP_DATE")
  private LocalDate agreementExpDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE", nullable = false)
  private LocalDate expDate;

  @Column(name = "REC_CREATED_DATE", nullable = false)
  private LocalDate recCreatedDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "PART_ID", nullable = false)
  private Long partId;

  @Column(name = "AGREEMENT_EFF_DATE")
  private LocalDate agreementEffDate;

  @Column(name = "AGREEMENT_EXP_DATE2")
  private LocalDate agreementExpDate2;

  @Column(name = "AGREEMENT_TIME_UNIT", length = 1)
  private String agreementTimeUnit;

  @Column(name = "AGREEMENT_LIMIT")
  private Long agreementLimit;

  @Column(name = "ROUTING_ID")
  private Long routingId;

  @Column(name = "ACTIVE_DATE")
  private LocalDate activeDate;

  @Column(name = "UPLOAD_TYPE", length = 1)
  private String uploadType;

  @Column(name = "PROD_ALIAS", length = 30)
  private String prodAlias;

  @Column(name = "PARENT_OFFER_ID")
  private Long parentOfferId;

  @Lob
  @Column(name = "EXT_ATTR")
  private String extAttr;
}
