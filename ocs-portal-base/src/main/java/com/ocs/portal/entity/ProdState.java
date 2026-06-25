package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "PROD_STATE")
public class ProdState implements Serializable {
    @Id
    @Column(name = "PROD_STATE", nullable = false)
    private Character id;

    @Column(name = "PROD_STD_STATE")
    private Character prodStdState;

    @Column(name = "PROD_STATE_NAME", nullable = false, length = 60)
    private String prodStateName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "PRIORITY", nullable = false)
    private Short priority;

    @Column(name = "BLOCK_FLAG", length = 60)
    private String blockFlag;

    @Column(name = "DIS_ORDER", nullable = false)
    private Short disOrder;

    @Column(name = "SP_ID")
    private Integer spId;

    public Character getId() {
        return id;
    }

    public void setId(Character id) {
        this.id = id;
    }

    public Character getProdStdState() {
        return prodStdState;
    }

    public void setProdStdState(Character prodStdState) {
        this.prodStdState = prodStdState;
    }

    public String getProdStateName() {
        return prodStateName;
    }

    public void setProdStateName(String prodStateName) {
        this.prodStateName = prodStateName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    public Short getDisOrder() {
        return disOrder;
    }

    public void setDisOrder(Short disOrder) {
        this.disOrder = disOrder;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

}