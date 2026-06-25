package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustBillDeliveryInfoHisId {

  @Column(name = "CUST_BILL_DELIVERY_INFO_ID", nullable = false)
  private Long custBillDeliveryInfoId;

  @Column(name = "SEQ", nullable = false)
  private Long seq;

}
