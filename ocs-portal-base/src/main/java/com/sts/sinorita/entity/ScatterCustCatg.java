package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SCATTER_CUST_CATG")
public class ScatterCustCatg implements Serializable {
    @Id
    @Column(name = "SCATTER_CUST_CATG_ID", nullable = false)
    private Long scatterCustCatgId;

    @Column(name = "CUST_CATG_ID", nullable = false)
    private Long custCatgId;

    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;
}
