package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RE_ACTION_PROD", schema = "STS")
public class ReActionProd {

    @Id
    @Column(name = "RECHARGE_PROD_ID", nullable = false, precision = 9)
    private Long rechargeProdId;

    @Column(name = "RE_ACTION_ID", nullable = false, precision = 6)
    private Long reActionId;

    @Column(name = "PERIOD_ID", precision = 9)
    private Long periodId;

    @Column(name = "DEPEND_PROD_SPEC_ID", precision = 9)
    private Long dependProdSpecId;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "PERIOD_REL_UNIT", length = 1)
    private String periodRelUnit;

    @Column(name = "OPERATION_FLAG", length = 1)
    private String operationFlag;

    @Column(name = "DEF_PERIOD", length = 1)
    private String defPeriod;

    @Column(name = "SP_ID", precision = 6)
    private Long spId;

    @Column(name = "FELLOW_NBR_TYPE_ID", precision = 6)
    private Long fellowNbrTypeId;
}
