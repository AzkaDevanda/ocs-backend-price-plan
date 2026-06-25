package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "ACC_NBR_TYPE")
public class AccNbrType implements Serializable {
    @Id
    @Column(name = "ACC_NBR_TYPE_ID", nullable = false)
    private Integer id;

    @Column(name = "ACC_NBR_TYPE_NAME", nullable = false, length = 60)
    private String accNbrTypeName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ACC_NBR_TYPE_CODE", length = 60)
    private String accNbrTypeCode;

}
