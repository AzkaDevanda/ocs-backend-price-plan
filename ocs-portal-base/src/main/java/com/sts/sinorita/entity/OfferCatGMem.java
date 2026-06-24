package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "OFFER_CATG_MEM")
public class OfferCatGMem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_cat_seq_generator")
    @SequenceGenerator(name = "offer_cat_seq_generator", sequenceName = "OFFER_CATG_MEM_ID_SEQ", allocationSize = 1)
    @Column(name = "OFFER_CATG_MEM_ID")
    private Long offerCatgMemId;

    @Column(name = "OFFER_CATG_ID", nullable = false)
    private Long offerCatgId;

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "CHILD_OFFER_CATG_ID", nullable = false)
    private Integer childOfferCatgId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;

    @Column(name = "SP_ID", nullable = false)
    private Integer spId;

    @Column(name = "EFF_DATE")
    @Temporal(TemporalType.DATE) // Assuming it's a date type (no time component)
    private Date effDate;

    @Column(name = "EXP_DATE")
    @Temporal(TemporalType.DATE) // Assuming it's a date type (no time component)
    private Date expDate;

}
