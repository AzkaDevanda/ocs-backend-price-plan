package com.sts.sinorita.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "DP_RULE")
public class DpRule implements Serializable {
    @Id
    @Column(name = "DP_ID", nullable = false)
    private Integer id;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Lob
    @Column(name = "RULE_SCRIPT", nullable = false)
    private String ruleScript;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcctItemTypeId() {
        return acctItemTypeId;
    }

    public void setAcctItemTypeId(Integer acctItemTypeId) {
        this.acctItemTypeId = acctItemTypeId;
    }

    public String getRuleScript() {
        return ruleScript;
    }

    public void setRuleScript(String ruleScript) {
        this.ruleScript = ruleScript;
    }

    public String getRuleComments() {
        return ruleComments;
    }

    public void setRuleComments(String ruleComments) {
        this.ruleComments = ruleComments;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public byte[] getScriptPage() {
        return scriptPage;
    }

    public void setScriptPage(byte[] scriptPage) {
        this.scriptPage = scriptPage;
    }

    public Integer getScriptTempletId() {
        return scriptTempletId;
    }

    public void setScriptTempletId(Integer scriptTempletId) {
        this.scriptTempletId = scriptTempletId;
    }

}