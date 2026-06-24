package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "ACCT_CATG_RULE", schema = "STS")
@Data
public class AcctCatgRule implements Serializable {
    @Id
    @Column(name = "ACCT_CATG_RULE_ID", nullable = false)
    private Long acctCatgRuleId;

    @Column(name = "ACCT_CATG_ID", nullable = false)
    private Long acctCatgId;

    @Column(name = "PARENT_ACCT_CATG_RULE_ID")
    private Long parentAcctCatgRuleId;

    @Column(name = "RELATION_TYPE", length = 1)
    private String relationType;

    @Column(name = "SP_ID")
    private Long spId;
}
