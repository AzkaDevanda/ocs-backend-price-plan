package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CUST_TYPE", schema = "STS")
public class CustTypeEntity {

    @Id
    @Column(name = "CUST_TYPE", length = 1, nullable = false)
    private Character custType;

    @Column(name = "CUST_TYPE_NAME", length = 60, nullable = false)
    private String custTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

}
