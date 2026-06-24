package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUST_BILL_DELIVERY_INFO")
public class CustBillDeliveryInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_bill_delivery_info_seq")
    @SequenceGenerator(name = "cust_bill_delivery_info_seq", sequenceName = "CUST_BILL_DELIVERY_INFO_ID_SEQ", allocationSize = 1)
    @Column(name = "CUST_BILL_DELIVERY_INFO_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "STD_ADDR_ID")
    private Long stdAddrId;

    @Column(name = "DETAIL_INFO", length = 4000)
    private String detailInfo;

    @Column(name = "FILE_TYPE", length = 1)
    private String fileType;

    @Column(name = "ZIPCODE", length = 255)
    private String zipcode;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "CC_EMAIL", length = 255)
    private String ccEmail;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "SMS_NBR", length = 60)
    private String smsNbr;

    @Column(name = "FAX_NBR", length = 60)
    private String faxNbr;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
        if (updateDate == null) {
            updateDate = LocalDateTime.now();
        }
    }

}
