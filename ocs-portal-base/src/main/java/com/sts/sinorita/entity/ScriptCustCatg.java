package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "SCRIPT_CUST_CATG")
@Data
public class ScriptCustCatg implements Serializable {

    @Id
    @Column(name = "CUST_CATG_ID", nullable = false)
    private Long custCatgId;

    @Column(name = "SERVICE_RULE_OBJECT")
    private Long serviceRuleObject;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;
}
