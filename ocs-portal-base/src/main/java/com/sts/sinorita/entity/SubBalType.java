package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SUB_BAL_TYPE")
@SequenceGenerator(name = "sub_bal_type_seq", sequenceName = "STS.SUB_BAL_TYPE_ID_SEQ", allocationSize = 1)
public class SubBalType implements Serializable {
    @Id
    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_bal_type_seq")
    private Integer id;

    @Column(name = "ACCT_RES_ID", nullable = false)
    private Integer acctResId;

    @Column(name = "PERIOD_ID", nullable = false)
    private Integer periodId;

    @Column(name = "CEIL_LIMIT")
    private Long ceilLimit;

    @Column(name = "FLOOR_LIMIT")
    private Long floorLimit;

    @Column(name = "DAILY_CEIL_LIMIT")
    private Long dailyCeilLimit;

    @Column(name = "DAILY_FLOOR_LIMIT")
    private Long dailyFloorLimit;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "MAX_DAYS")
    private Integer maxDays;

    @Column(name = "LIMIT_SUBS")
    private Character limitSubs;

    @Column(name = "PERIOD_REL_UNIT")
    private Character periodRelUnit;

    @Column(name = "ABS_EXP_OFFSET")
    private Long absExpOffset;

    @Column(name = "EXTEND_RULE")
    private Character extendRule;

    @Column(name = "BAL_FLAGS", length = 8)
    private String balFlags;


}