package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BIZ_CUST", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BizCust {

  @Id
  @Column(name = "BIZ_CUST_ID")
  private Long bizCustId;

  @Column(name = "BUSINESS_TYPE")
  private Character businessType;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "LANDLINE_NUMBER")
  private String landlineNumber;

  @Column(name = "FAX")
  private String fax;

  @Column(name = "REGISTERED_ADDRESS")
  private String registeredAddress;

  @Column(name = "SCALE")
  private String scale;

  @Column(name = "CORE_BUSINESS")
  private String coreBusiness;

  @Column(name = "FIXED_ASSET_AMOUNT")
  private String fixedAssetAmount;

  @Column(name = "DECISION_MAKERS")
  private String decisionMakers;

  @Column(name = "DECISION_MAKERS_CONTACT")
  private String decisionMakersContact;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "COMPANY_NBR")
  private String companyNbr;

  @Column(name = "NO_VAT_REASON")
  private String noVatReason;
}
