package com.ocs.portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BILL_EX")
public class BillEx {
  @Id
  @Column(name = "BILL_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "SUBS_EVENT_CHARGE", nullable = false)
  private Long subsEventCharge;

  @Column(name = "PAY_IN_TIME")
  private Boolean payInTime;

}