package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "TABULAR_CUST_CATG")
public class TabularCustCatg implements Serializable {

    @Id
    @Column(name = "CUST_CATG_ID", nullable = false)
    private Long custCatgId;

    @Column(name = "SP_ID")
    private Long spId;
}
