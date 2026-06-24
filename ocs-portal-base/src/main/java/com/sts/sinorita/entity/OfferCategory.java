package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "OFFER_CATG")
public class OfferCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offercat_seq_generator")
    @SequenceGenerator(name = "offercat_seq_generator", sequenceName = "OFFER_CATG_ID_SEQ", allocationSize = 1)
    @Column(name = "OFFER_CATG_ID")
    private Integer offerCatgId;

    @Column(name = "OFFER_CATG_TYPE", nullable = false, length = 1)
    private Character offerCatgType;

    @Column(name = "OFFER_CATG_CLASS", length = 1)
    private Character offerCatgClass;

    @Column(name = "OFFER_CATG_NAME", nullable = false, length = 100)
    private String offerCatgName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "OFFER_CATG_CODE", nullable = false, length = 50)
    private String offerCatgCode;

    @Column(name = "EFF_DATE")
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "STATE", length = 1)
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "SP_ID", nullable = false)
    private Integer spId;

    @Column(name = "POLICY_FLAG", length = 1)
    private Character policyFlag;

    @Column(name = "SEQ")
    private Integer seq;

}
