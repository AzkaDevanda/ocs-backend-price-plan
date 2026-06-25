package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEPOSIT_PRICE")
public class DepositPrice implements Serializable {
    @Id
    @Column(name = "PRICE_ID", nullable = false)
    private Long id;

    @Column(name = "DEPOSIT_TYPE_ID", nullable = false)
    private Integer depositTypeId;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "PARAM", length = 4000)
    private String param;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;

    @Column(name = "SP_ID")
    private Integer spId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDepositTypeId() {
        return depositTypeId;
    }

    public void setDepositTypeId(Integer depositTypeId) {
        this.depositTypeId = depositTypeId;
    }

    public Integer getScriptTempletId() {
        return scriptTempletId;
    }

    public void setScriptTempletId(Integer scriptTempletId) {
        this.scriptTempletId = scriptTempletId;
    }

    public byte[] getScriptPage() {
        return scriptPage;
    }

    public void setScriptPage(byte[] scriptPage) {
        this.scriptPage = scriptPage;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRuleComments() {
        return ruleComments;
    }

    public void setRuleComments(String ruleComments) {
        this.ruleComments = ruleComments;
    }

    public String getRuleScript() {
        return ruleScript;
    }

    public void setRuleScript(String ruleScript) {
        this.ruleScript = ruleScript;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }
}