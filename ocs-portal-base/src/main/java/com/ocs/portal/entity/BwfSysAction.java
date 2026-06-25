package com.ocs.portal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "BWF_SYS_ACTION")
public class BwfSysAction implements Serializable {
    @Id
    @Column(name = "SYS_ACTION_ID", nullable = false)
    private Integer id;

    @Column(name = "STEP_ID", nullable = false)
    private Integer stepId;

    @Column(name = "SYS_ACTION_NAME", nullable = false, length = 60)
    private String sysActionName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Lob
    @Column(name = "EXT_SCRIPT")
    private String extScript;

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

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getSysActionName() {
        return sysActionName;
    }

    public void setSysActionName(String sysActionName) {
        this.sysActionName = sysActionName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getExtScript() {
        return extScript;
    }

    public void setExtScript(String extScript) {
        this.extScript = extScript;
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