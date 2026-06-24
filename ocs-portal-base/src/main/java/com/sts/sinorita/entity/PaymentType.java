package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENT_TYPE")
public class PaymentType implements Serializable {
    @Id
    @Column(name = "PAYMENT_TYPE", nullable = false)
    private Character paymentType;

    @Column(name = "PAYMENT_TYPE_NAME", nullable = false, length = 60)
    private String paymentTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;
}
