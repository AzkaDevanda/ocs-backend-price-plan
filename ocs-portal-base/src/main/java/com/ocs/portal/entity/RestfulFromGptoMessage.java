package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RESTFUL_FROMGPTO_MESSAGE")
public class RestfulFromGptoMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESTFUL_ID", nullable = false)
    private Long restfulId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "OPERATION_TYPE")
    private Long operationType;

    @Lob
    @Column(name = "MSG")
    private String msg;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "CUST_NAME", length = 200)
    private String custName;

    @Column(name = "ACCT_NUMBER", length = 200)
    private String acctNumber;

    @Column(name = "SERVICE_NUMBER", length = 200)
    private String serviceNumber;
}
