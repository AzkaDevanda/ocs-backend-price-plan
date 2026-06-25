package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "SCRIPT_TEMPLET")
public class ScriptTemplet implements Serializable {
    @Id
    @Column(name = "SCRIPT_TEMPLET_ID", nullable = false)
    private Integer id;

    @Column(name = "SCRIPT_TEMPLET_NAME", nullable = false, length = 60)
    private String scriptTempletName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TEMPLET_CONTENT")
    private byte[] templetContent;

    @Column(name = "TEMPLET_PARAM")
    private byte[] templetParam;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Boolean templateFlag;

    @Column(name = "SRC_SCRIPT_TEMPLATE_ID")
    private Integer srcScriptTemplateId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SCRIPT_TEMPLET_GROUP")
    private Boolean scriptTempletGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScriptTempletName() {
        return scriptTempletName;
    }

    public void setScriptTempletName(String scriptTempletName) {
        this.scriptTempletName = scriptTempletName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getTempletContent() {
        return templetContent;
    }

    public void setTempletContent(byte[] templetContent) {
        this.templetContent = templetContent;
    }

    public byte[] getTempletParam() {
        return templetParam;
    }

    public void setTempletParam(byte[] templetParam) {
        this.templetParam = templetParam;
    }

    public Boolean getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Boolean templateFlag) {
        this.templateFlag = templateFlag;
    }

    public Integer getSrcScriptTemplateId() {
        return srcScriptTemplateId;
    }

    public void setSrcScriptTemplateId(Integer srcScriptTemplateId) {
        this.srcScriptTemplateId = srcScriptTemplateId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Boolean getScriptTempletGroup() {
        return scriptTempletGroup;
    }

    public void setScriptTempletGroup(Boolean scriptTempletGroup) {
        this.scriptTempletGroup = scriptTempletGroup;
    }

}