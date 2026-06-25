package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_METHOD")
public class PaymentMethod implements Serializable {
    @Id
    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "PAYMENT_TYPE", nullable = false, length = 1)
    private Character paymentType;

    @Column(name = "PAYMENT_METHOD_NAME", nullable = false, length = 60)
    private String paymentMethodName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "PAYMENT_METHOD_CODE", length = 60)
    private String paymentMethodCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SYSTEM_RESERVED", length = 1)
    private Character systemReserved = 'N';

    @Column(name = "GROUP_TYPE")
    private Character groupType;
}
