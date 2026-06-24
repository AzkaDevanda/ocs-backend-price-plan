package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "TRIGGER_TYPE")
public class TriggerType implements Serializable {
    @Id
    @Column(name = "TRIGGER_TYPE", nullable = false)
    private Character id;

    @Column(name = "TRIGGER_TYPE_NAME", nullable = false, length = 60)
    private String triggerTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    public Character getId() {
        return id;
    }

    public void setId(Character id) {
        this.id = id;
    }

    public String getTriggerTypeName() {
        return triggerTypeName;
    }

    public void setTriggerTypeName(String triggerTypeName) {
        this.triggerTypeName = triggerTypeName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}