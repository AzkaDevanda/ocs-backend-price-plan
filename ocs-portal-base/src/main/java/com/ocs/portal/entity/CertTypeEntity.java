package com.ocs.portal.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CERT_TYPE", schema = "STS")
public class CertTypeEntity {

    @Id
    @Column(name = "CERT_TYPE_ID", nullable = false)
    private Integer certTypeId;

    @Column(name = "CUST_TYPE", length = 1)
    private String custType;

    @Column(name = "CERT_TYPE_NAME", nullable = false, length = 60)
    private String certTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "NBR_MASK", length = 255)
    private String nbrMask;

    @Column(name = "NBR_EXAMPLE", length = 255)
    private String nbrExample;

    @Column(name = "MAX_NBR_LENGTH")
    private Long maxNbrLength;

    @Column(name = "MIN_NBR_LENGTH")
    private Long minNbrLength;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CERT_TYPE_CODE", length = 30)
    private String certTypeCode;


}
