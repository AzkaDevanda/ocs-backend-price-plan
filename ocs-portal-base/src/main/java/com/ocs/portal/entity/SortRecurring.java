package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "SORT_RECURRING")
public class SortRecurring implements Serializable {
    @Id
    @Column(name = "RE_ID", nullable = false)
    private Integer id;

    @Column(name = "WORKFLOW_ID", nullable = false)
    private Integer workflowId;

    @Column(name = "PRE_WORKFLOW_ID")
    private Integer preWorkflowId;

    @Column(name = "POST_WORKFLOW_ID")
    private Integer postWorkflowId;

    @Column(name = "SP_ID")
    private Integer spId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public Integer getPreWorkflowId() {
        return preWorkflowId;
    }

    public void setPreWorkflowId(Integer preWorkflowId) {
        this.preWorkflowId = preWorkflowId;
    }

    public Integer getPostWorkflowId() {
        return postWorkflowId;
    }

    public void setPostWorkflowId(Integer postWorkflowId) {
        this.postWorkflowId = postWorkflowId;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}