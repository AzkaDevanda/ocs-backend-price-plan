package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFER_GROUP")
public class OfferGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_group_seq")
    @SequenceGenerator(name = "offer_group_seq", sequenceName = "OFFER_GROUP_ID_SEQ", allocationSize = 1)
    @Column(name = "OFFER_GROUP_ID", nullable = false)
    private Long offerGroupId;

    @Column(name = "OFFER_GROUP_NAME", nullable = false)
    private String offerGroupName;

    @Column(name = "OFFER_GROUP_CODE")
    private String offerGroupCode;

    @Column(name = "OFFER_GROUP_TYPE", length = 1, nullable = false)
    private Character offerGroupType;

    @Column(name = "GROUP_TYPE", length = 1, nullable = false)
    private Character groupType;

    @Column(name = "UPPER_LIMIT")
    private Integer upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Integer lowerLimit;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", length = 1, nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "SHARE_FLAG", length = 1, nullable = false)
    private Character shareFlag;

    @Column(name = "INDEP_PROD_SPEC_ID")
    private Long indepProdSpecId;

    @Column(name = "OFFER_VER_ID")
    private Long offerVerId;

    @Column(name = "NETWORK_TYPE", length = 1)
    private Character networkType;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;
}
