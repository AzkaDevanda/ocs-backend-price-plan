package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "CUST_CATG_RULE")
@Data
public class CustCatgRule implements Serializable {
    @Id
    @Column(name = "CUST_CATG_RULE_ID", nullable = false)
    private Long custCatgRuleId;

    @Column(name = "CUST_CATG_ID", nullable = false)
    private Long custCatgId;

    @Column(name = "PARENT_CUST_CATG_RULE_ID")
    private Long parentCustCatgRuleId;

    @Column(name = "RELATION_TYPE", length = 1)
    private String relationType;

    @Column(name = "SP_ID")
    private Long spId;
}
