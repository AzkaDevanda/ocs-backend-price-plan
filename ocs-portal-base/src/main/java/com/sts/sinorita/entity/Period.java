package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "PERIOD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "period_seq", sequenceName = "STS.PERIOR_ID_SEQ", allocationSize = 1)
public class Period implements Serializable {
    @Id
    @Column(name = "PERIOD_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "period_seq")
    private Integer id;

    @Column(name = "ABS_EFF_DATE")
    private LocalDate absEffDate;

    @Column(name = "ABS_EXP_DATE")
    private LocalDate absExpDate;

    @Column(name = "REL_EFF_OFFSET")
    private Integer relEffOffset;

    @Column(name = "REL_EFF_UNIT")
    private Character relEffUnit;

    @Column(name = "REL_EXP_OFFSET")
    private Integer relExpOffset;

    @Column(name = "REL_EXP_UNIT")
    private Character relExpUnit;

    @Column(name = "REL_EFF_TIME")
    private LocalTime relEffTime;

    @Column(name = "REL_EXP_TIME")
    private LocalTime relExpTime;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "DAY_OFFSET")
    private Integer dayOffset;

}