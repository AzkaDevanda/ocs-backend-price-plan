package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACM_TRIGGER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "acm_trigger_seq", sequenceName = "ACM_TRIGGER_ID_SEQ", allocationSize = 1)
public class AcmTrigger implements Serializable {
    @Id
    @Column(name = "TRIGGER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acm_trigger_seq")
    private Integer id;

    @Column(name = "RESOURCE_ID")
    private Integer resourceId;

    @Column(name = "TRIGGER_TYPE", nullable = false)
    private Character triggerType;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REF_VALUE_ID")
    private Integer refValueId;

    @Column(name = "DESTINATION")
    private Character destination;

}