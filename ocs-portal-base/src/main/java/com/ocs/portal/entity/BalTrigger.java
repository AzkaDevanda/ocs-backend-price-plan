package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAL_TRIGGER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalTrigger implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAL_TRIGGER_ID_SEQ_generator")
    @SequenceGenerator(name = "BAL_TRIGGER_ID_SEQ_generator", sequenceName = "BAL_TRIGGER_ID_SEQ", allocationSize = 1)
    @Column(name = "TRIGGER_ID", nullable = false)
    private Integer id;

    @Column(name = "ACCT_RES_ID_LIST")
    private String acctResIdList;

    @Column(name = "TRIGGER_TYPE", nullable = false)
    private Character triggerType;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "IS_LIMIT_BALANCE")
    private Character isLimitBalance;

    @Column(name = "DESTINATION")
    private Character destination;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

}