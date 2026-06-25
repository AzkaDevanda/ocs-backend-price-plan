package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUST_PRICE_BOOK_PRICE_PLAN", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustPriceBookPricePlan {
  @Id
  @Column(name = "PRICE_BOOK_PRICE_PLAN_ID", nullable = false)
  private Long priceBookPricePlanId;

  @Column(name = "PRICE_BOOK_VER_ID", nullable = false)
  private Long priceBookVerId;

  @Column(name = "PRICE_PLAN_ID", nullable = false)
  private Long pricePlanId;

  @Column(name = "SP_ID")
  private Integer spId;
}
