package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "LIFECYCLE_TYPE")
@Data
public class LifeCycleType {
    @Id
    @Column(name = "LIFECYCLE_TYPE")
    private Integer lifeCycleType;
    @Column(name = "LIFECYCLE_TYPE_NAME", nullable = false, length = 60)
    private String lifecycleTypeName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Lob
    @Column(name = "EXT_ATTR")
    private String extAttr;
}
