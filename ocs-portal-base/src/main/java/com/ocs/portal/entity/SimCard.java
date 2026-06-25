package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SIM_CARD")
public class SimCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sim_card_id_seq")
    @SequenceGenerator(name = "sim_card_id_seq", sequenceName = "SIM_CARD_ID_SEQ", allocationSize = 1)
    @Column(name = "SIM_CARD_ID", nullable = false)
    private Long simCardId;

    @Column(name = "SIM_TYPE_ID")
    private Integer simTypeId;

    @Column(name = "ICCID", nullable = false)
    private String iccid;

    @Column(name = "HLR_ID")
    private Integer hlrId;

    @Column(name = "IMSI", nullable = false)
    private String imsi;

    @Column(name = "PIN1")
    private String pin1;

    @Column(name = "PUK1")
    private String puk1;

    @Column(name = "PIN2")
    private String pin2;

    @Column(name = "PUK2")
    private String puk2;

    @Column(name = "KI")
    private String ki;

    @Column(name = "STAFF_ID")
    private Integer staffId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "SIM_STATE", nullable = false)
    private String simState;

    @Column(name = "AREA_ID", nullable = false)
    private Integer areaId;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "IMSI2")
    private String imsi2;

    @Column(name = "KI2")
    private String ki2;

    @Column(name = "ESN")
    private String esn;

    @Column(name = "INJECT_FLAG")
    private String injectFlag;

    @Column(name = "ADM")
    private String adm;

    @Column(name = "RECYCLE_FLAG")
    private String recycleFlag;

    @Column(name = "CHECK_SUM")
    private String checkSum;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "K4")
    private String k4;

    @Column(name = "IS_BINDING_FLAG")
    private String isBindingFlag;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PARTY_TYPE")
    private String partyType;

    @Column(name = "PARTY_CODE")
    private String partyCode;
}
