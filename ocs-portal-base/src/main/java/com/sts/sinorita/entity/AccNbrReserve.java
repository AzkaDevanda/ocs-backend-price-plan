package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.AccNbrReserveId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ACC_NBR_RESERVE")
public class AccNbrReserve implements Serializable {

    @EmbeddedId
    private AccNbrReserveId id;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "RESERVE_TYPE", nullable = false)
    private String reserveType;

    @Column(name = "CERT_TYPE_ID")
    private Integer certTypeId;

    @Column(name = "CERT_NBR")
    private String certNbr;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "PWD")
    private String pwd;

    @Column(name = "RESERVE_DATE", nullable = false)
    private LocalDate reserveDate;

    @Column(name = "RESERVE_EXP_DATE")
    private LocalDate reserveExpDate;

    @Column(name = "PARTY_TYPE")
    private String partyType;

    @Column(name = "PARTY_CODE")
    private String partyCode;

    @Column(name = "RESERVE_PARTY_TYPE", nullable = false)
    private String reservePartyType;

    @Column(name = "RESERVE_PARTY_CODE")
    private String reservePartyCode;

    @Column(name = "RESERVE_COMMETS")
    private String reserveCommet;

    @Column(name = "CANCEL_DATE")
    private LocalDate cancelDate;

    @Column(name = "CANCEL_PARTY_TYPE")
    private String cancelPartyType;

    @Column(name = "CANCEL_PARTY_CODE")
    private String cancelPartyCode;

    @Column(name = "SP_ID")
    private Integer spId;
}
