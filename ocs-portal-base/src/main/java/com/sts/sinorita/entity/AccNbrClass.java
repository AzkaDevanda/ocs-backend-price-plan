package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "ACC_NBR_CLASS")
public class AccNbrClass implements Serializable {
    @Id
    @Column(name = "ACC_NBR_CLASS_ID", nullable = false)
    private Integer accNbrClassId;

    @Column(name = "ACC_NBR_CLASS_NAME", nullable = false, length = 60)
    private String accNbrClassName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "ACC_NBR_PRICE")
    private Long accNbrPrice;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "SERVICE_RULE")
    private Integer serviceRule;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ACC_NBR_CLASS_CODE", length = 60)
    private String accNbrClassCode;
}
