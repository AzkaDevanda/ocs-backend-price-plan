package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "OFFER_VER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferVer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_ver_seq")
    @SequenceGenerator(name = "offer_ver_seq", sequenceName = "OFFER_VER_ID_SEQ", allocationSize = 1)
    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer id;

    @Column(name = "OFFER_ID")
    private Integer offerId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Builder.Default
    @Column(name = "SEQ")
    private Integer seq = 1;

    @Builder.Default
    @Column(name = "SP_ID")
    private Integer spId = 0;

    @Builder.Default
    @Column(name = "STATE", length = 1, nullable = false)
    private Character state = 'A';

    @Column(name = "REF_OFFER_VER_ID")
    private Integer refOfferVerId;
}
