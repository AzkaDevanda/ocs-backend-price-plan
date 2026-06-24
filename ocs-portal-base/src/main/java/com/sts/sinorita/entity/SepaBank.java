package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SEPA_BANK")
public class SepaBank implements Serializable {
    @Id
    @Column(name = "BANK_ID", nullable = false)
    private Integer bankId;

    @Column(name = "BIC", nullable = false, length = 30)
    private String bic;

    @Column(name = "SEPA_STATE", nullable = false, length = 1)
    private Character sepaState;

    @Column(name = "IBAN_FORMAT", nullable = false, length = 30)
    private String ibanFormat;

    @Column(name = "SP_ID")
    private Integer spId;
}
