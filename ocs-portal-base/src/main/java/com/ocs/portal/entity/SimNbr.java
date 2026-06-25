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
import java.time.LocalDate;

@Data
@Entity
@Table(name = "SIM_NBR")
public class SimNbr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sim_nbr_id_seq")
    @SequenceGenerator(name = "sim_nbr_id_seq", sequenceName = "SIM_NBR_ID_SEQ", allocationSize = 1)
    @Column(name = "SIM_NBR_ID")
    private Long simNbrId;

    @Column(name = "SIM_CARD_ID")
    private Long simCardId;

    @Column(name = "ACC_NBR_ID")
    private Long accNbrId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "STAFF_ID")
    private Long staffId;

    @Column(name = "FIRST_FLAG")
    private String firstFlag;

    @Column(name = "SP_ID")
    private Integer spId;
}
