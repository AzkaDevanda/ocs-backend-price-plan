package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "SCRIPT_SUBS_CATG")
public class ScriptSubsCatg implements Serializable {
    @Id
    @Column(name = "SUBS_CATG_ID", nullable = false)
    private Long subsCatgId;

    @Column(name = "SERVICE_RULE_OBJECT")
    private Long serviceRuleObject;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;
}
