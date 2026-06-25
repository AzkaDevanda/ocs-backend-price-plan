package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUST_SPECIAL_GROUP")
public class CustSpecialGroup {

    @Id
    @Column(name = "CUST_SPECIAL_GROUP_ID", nullable = false)
    private Long custSpecialGroupId;

    @Column(name = "SPECIAL_GROUP_TYPE_ID", nullable = false)
    private Long specialGroupTypeId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "CUST_NAME", length = 255)
    private String custName;

    @Column(name = "CERT_ID")
    private Long certId;

    @Column(name = "CERT_TYPE_ID")
    private Long certTypeId;

    @Column(name = "CERT_NBR", length = 120)
    private String certNbr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "PARTY_TYPE", length = 1, nullable = false)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;
}
