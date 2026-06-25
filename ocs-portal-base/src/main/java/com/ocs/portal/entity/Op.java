package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "OP")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Op implements Serializable {
    @Id
    @Column(name = "OP_ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Column(name = "PARAM", length = 4000)
    private String param;

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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