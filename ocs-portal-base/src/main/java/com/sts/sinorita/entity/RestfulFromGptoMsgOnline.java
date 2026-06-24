package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RESTFUL_FROMGPTO_MSG_ONLINE")
public class RestfulFromGptoMsgOnline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESTFUL_ONLINE_ID", nullable = false)
    private Long restfulOnlineId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "OPERATION_TYPE")
    private Long operationType;

    @Column(name = "INTERFACE_TYPE", length = 20)
    private String interfaceType;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Lob
    @Column(name = "MSG")
    private String msg;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CUST_NAME", length = 200)
    private String custName;

    @Column(name = "ACCT_NUMBER", length = 200)
    private String acctNumber;

    @Column(name = "SERVICE_NUMBER", length = 200)
    private String serviceNumber;
}
