package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class PartSpBankId implements Serializable {
    @Column(name = "SP_ID", nullable = false)
    private Integer spId;

    @Column(name = "PAYMENT_TYPE", nullable = false, length = 30)
    private String paymentType;
}
