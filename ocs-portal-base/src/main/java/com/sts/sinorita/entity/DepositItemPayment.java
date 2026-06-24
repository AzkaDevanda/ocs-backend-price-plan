package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DEPOSIT_ITEM_PAYMENT", schema = "STS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositItemPayment {

    @Id
    @Column(name = "DEPOSIT_ITEM_ID", nullable = false, precision = 12)
    private Long depositItemId;

    @Column(name = "EVENT_PAYMENT_ID", nullable = false, precision = 12)
    private Long eventPaymentId;

    @Column(name = "SP_ID", precision = 6)
    private Long spId;

}