package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "ACM_CALC")
@SequenceGenerator(name = "ACM_VALUE_ID_SEQ", sequenceName = "REF_VALUE_ID_SEQ", allocationSize = 1)
public class AcmCalc implements Serializable {

    @Id
    @Column(name = "ACM_CALC_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACM_VALUE_ID_SEQ")
    private Integer id;

    @Column(name = "RESOURCE_ID")
    private Integer resourceId;

    @Column(name = "UP_ID", nullable = false)
    private Integer upId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "TIME_SPAN_UP_ID")
    private Integer timeSpanUpId;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REF_VALUE_ID")
    private Integer refValueId;

}