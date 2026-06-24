package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "BANK")
public class Bank implements Serializable {
    @Id
    @Column(name = "BANK_ID", nullable = false)
    private Integer bankId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "BANK_NAME", nullable = false, length = 120)
    private String bankName;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "DIRECT_DEBIT_FLAG", length = 1)
    private Character directDebitFlag;

    @Column(name = "BANK_CODE", length = 255)
    private String bankCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "COUNTRY_CODE", nullable = false, length = 30)
    private String countryCode = "0";
}
