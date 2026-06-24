package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "CUST_CATG_ATTR_VALUE")
@Data
public class CustCatgAttrValue implements Serializable {
    @Id
    @Column(name = "CUST_CATG_ATTR_VALUE_ID", nullable = false)
    private Long custCatgAttrValueId;

    @Column(name = "CUST_CATG_RULE_ID")
    private Long custCatgRuleId;

    @Column(name = "BASE_ATTR_ID", nullable = false, length = 60)
    private String baseAttrId;

    @Column(name = "ATTR_TYPE", length = 1)
    private String attrType;

    @Column(name = "OPERATOR", nullable = false, length = 30)
    private String operator;

    @Column(name = "VALUE", nullable = false, length = 120)
    private String value;

    @Column(name = "SP_ID")
    private Long spId;
}
