package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACM_REF")
public class AcmRef implements Serializable {
    @Id
    @Column(name = "ACM_REF_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acm_ref_generator")
    @SequenceGenerator(name = "acm_ref_generator", sequenceName = "ACM_REF_ID_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "PRICE_VER_ID", nullable = false)
    private Integer priceVerId;

    @Column(name = "ADJUST_METHOD", nullable = false)
    private Character adjustMethod;

    @Column(name = "RESOURCE_ID")
    private Integer resourceId;

    @Column(name = "ACM_TIME_SPAN_ID")
    private Integer acmTimeSpanId;

    @Column(name = "RATE", nullable = false)
    private Integer rate;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "EFF_VALUE")
    private Long effValue;

    @Column(name = "EXP_VALUE")
    private Long expValue;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "REF_VALUE_ID")
    private Integer refValueId;

}