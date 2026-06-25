package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "PROD")
public class Prod implements Serializable {
  @Id
  @Column(name = "PROD_ID", nullable = false)
  private Long id;

  @Column(name = "COMPLETED_DATE", nullable = false)
  private LocalDate completedDate;

  @Column(name = "PROD_STATE", nullable = false)
  private Character prodState;

  @Column(name = "OFFER_ID")
  private Integer offerId;

  @Column(name = "SUBS_PLAN_ID")
  private Integer subsPlanId;

  @Column(name = "PACKAGE_FLAG")
  private Character packageFlag;

  @Column(name = "PARENT_PROD_ID")
  private Long parentProdId;

  @Column(name = "INDEP_PROD_ID")
  private Long indepProdId;

  @Column(name = "PROD_STATE_DATE", nullable = false)
  private LocalDate prodStateDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDate updateDate;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDate createdDate;

  @Column(name = "STATE", nullable = false)
  private Character state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDate stateDate;

  @Column(name = "BLOCK_REASON", length = 60)
  private String blockReason;

  @Column(name = "PROD_EXP_DATE")
  private LocalDate prodExpDate;

  @ColumnDefault("'N'")
  @Column(name = "NEED_UPLOAD")
  private Character needUpload;

  @Column(name = "AGREEMENT_EFF_DATE")
  private LocalDate agreementEffDate;

  @Column(name = "AGREEMENT_EXP_DATE")
  private LocalDate agreementExpDate;

  @Column(name = "AGREEMENT_TIME_UNIT")
  private Character agreementTimeUnit;

  @Column(name = "AGREEMENT_LIMIT")
  private Integer agreementLimit;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "PROMOTION_ITEM_ID")
  private Long promotionItemId;

  @Column(name = "ROUTING_ID")
  private Integer routingId;

  @Column(name = "ACTIVE_DATE")
  private LocalDate activeDate;

  @Column(name = "UPLOAD_TYPE")
  private Character uploadType;

  @Column(name = "PROD_ALIAS", length = 30)
  private String prodAlias;

  @Column(name = "PARENT_OFFER_ID")
  private Integer parentOfferId;

}