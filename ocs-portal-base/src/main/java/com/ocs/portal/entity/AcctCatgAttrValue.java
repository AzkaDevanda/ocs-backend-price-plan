package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "ACCT_CATG_ATTR_VALUE")
@Data
public class AcctCatgAttrValue implements Serializable {
    @Id
    @Column(name = "ACCT_CATG_ATTR_VALUE_ID", nullable = false)
    private Long acctCatgAttrValueId;

    @Column(name = "ACCT_CATG_RULE_ID")
    private Long acctCatgRuleId;

    @Column(name = "VALUE", nullable = false, length = 120)
    private String value;

    @Column(name = "OPERATOR", nullable = false, length = 30)
    private String operator;

    @Column(name = "BASE_ATTR_ID", nullable = false, length = 60)
    private String baseAttrId;

    @Column(name = "ATTR_TYPE", length = 1)
    private String attrType;

    @Column(name = "SP_ID")
    private Long spId;
}
