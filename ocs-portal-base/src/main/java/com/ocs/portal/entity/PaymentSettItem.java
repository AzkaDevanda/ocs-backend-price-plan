package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAYMENT_SETT_ITEM")
public class PaymentSettItem {
  @EmbeddedId
  private PaymentSettItemId id;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "SETTLE_CHARGE")
  private Long settleCharge;

  @Column(name = "BILL_ID")
  private Long billId;

}