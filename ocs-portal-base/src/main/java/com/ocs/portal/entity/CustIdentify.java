package com.ocs.portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUST_IDENTIFY", schema = "STS", indexes = {
        @Index(name = "IDX_CUST_IDENTIFY", columnList = "IDENTIFY_TYPE, IDENTIFY_VALUE"),
        @Index(name = "IDX_CUST_IDENTIFY_CUST_ID", columnList = "CUST_ID")
})
@Getter
@Setter
public class CustIdentify {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_identify_seq_generator")
    @SequenceGenerator(name = "cust_identify_seq_generator", sequenceName = "CUST_IDENTIFY_ID_SEQ", allocationSize = 1)
    @Column(name = "CUST_IDENTIFY_ID", nullable = false)
    private Long custIdentifyId;

    @Column(name = "IDENTIFY_TYPE", nullable = false, length = 1)
    private String identifyType;

    @Column(name = "IDENTIFY_VALUE", nullable = false, length = 255)
    private String identifyValue;

    @Column(name = "CUST_TYPE", nullable = false, length = 1)
    private String custType;

    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "SP_ID")
    private Long spId;
}
