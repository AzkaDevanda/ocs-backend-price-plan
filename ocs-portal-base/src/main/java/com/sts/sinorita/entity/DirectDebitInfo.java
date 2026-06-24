package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "DIRECT_DEBIT_INFO")
public class DirectDebitInfo implements Serializable {
    @Id
    @Column(name = "AR_FILE_DEAL_CONFIG_ID", nullable = false)
    private Integer arFileDealConfigId;

    @Column(name = "BANK_ID")
    private Integer bankId;

    @Column(name = "SP_ID")
    private Integer spId;
}
