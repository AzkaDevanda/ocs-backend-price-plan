package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACM_TYPE")
public class AcmType {

    @Id
    @Column(name = "ACM_TYPE", nullable = false)
    private Character id;

    @Column(name = "ACM_TYPE_NAME", nullable = false, length = 60)
    private String acmTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    public Character getId() {
        return id;
    }

    public void setId(Character id) {
        this.id = id;
    }

    public String getAcmTypeName() {
        return acmTypeName;
    }

    public void setAcmTypeName(String acmTypeName) {
        this.acmTypeName = acmTypeName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}