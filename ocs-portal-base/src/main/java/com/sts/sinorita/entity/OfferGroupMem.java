package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFER_GROUP_MEM")
public class OfferGroupMem {

    @Id
    @Column(name = "OFFER_GROUP_MEM_ID")
    private Integer id;

    @Column(name = "OFFER_GROUP_ID")
    private Integer offerGroupId;

    @Column(name = "OFFER_ID")
    private Integer offerId;

    @Column(name = "CHILD_OFFER_GROUP_ID")
    private Integer childOfferGroupId;

    @Column(name = "AGREEMENT_PERIOD")
    private Integer agreementPeriod;

    @Column(name = "TIME_UNIT")
    private Character timeUnit;

    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;

    @Column(name = "HIDE_FLAG")
    private Character hideFlag;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "AGREEMENT_EFF_TYPE")
    private Character agreementEffType;

    @Column(name = "UPPER_LIMIT")
    private Integer upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Integer lowerLimit;

    @Column(name = "DEFAULT_NUM")
    private Integer defaultNum;

//    @ManyToOne
//    @JoinColumn(name = "offer_offer_id")
//    private Offer offer;
//
//    @ManyToOne
//    @JoinColumn(name = "offer_group_offer_group_id")
//    private OfferGroup offerGroup;
//
//    private List<PackageMem> packageMemList;

}
