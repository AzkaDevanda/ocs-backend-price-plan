package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TABULAR_ACCT_CATG")
@Data
public class TabularAcctCatg  implements Serializable{

    @Id
    @Column(name = "ACCT_CATG_ID", nullable = false)
    private Long acctCatgId;

    @Column(name = "SP_ID")
    private Long spId;
}
