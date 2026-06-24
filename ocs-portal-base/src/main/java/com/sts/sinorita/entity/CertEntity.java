package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CERT", schema = "STS")
public class CertEntity {

    @Id
    @Column(name = "CERT_ID", nullable = false)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cert_seq_generator")
    // @SequenceGenerator(name = "cert_seq_generator", sequenceName = "CERT_ID_SEQ", allocationSize = 1)
    private Long certId;

    @Column(name = "CERT_TYPE_ID", nullable = false)
    private Integer certTypeId;

    @Column(name = "CERT_NBR", nullable = false, length = 120)
    private String certNbr;

    @Column(name = "ISSUE_ORG", length = 120)
    private String issueOrg;

    @Column(name = "ISSUE_DATE")
    // @Temporal(TemporalType.DATE)
    private LocalDateTime issueDate;

    @Column(name = "EFF_DATE")
    // @Temporal(TemporalType.DATE)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    // @Temporal(TemporalType.DATE)
    private LocalDateTime expDate;

    @Column(name = "CERT_ADDRESS", length = 255)
    private String certAddress;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ISSUE_COUNTRY")
    private Long issueCountry;

}
