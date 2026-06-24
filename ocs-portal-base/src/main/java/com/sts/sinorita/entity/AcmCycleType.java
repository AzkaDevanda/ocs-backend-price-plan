package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table()
public class AcmCycleType implements Serializable {

    @Id
    @Column(name = "ACM_CYCLE_TYPE_ID", nullable = false)
    private Integer acmCycleTypeId;

    @Column(name = "TIME_UNIT")
    private Character timeUnit;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;

    @Column(name = "REF_TYPE")
    private Character refType;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ADJUST_BEGIN_DATE_MODE")
    private Character adjustBeginDateMode;

    @Column(name = "OFFSET_DAYS")
    private Integer offsetDays;

    @Column(name = "ACCURACY_CLASS")
    private Character accuracyClass;

}
