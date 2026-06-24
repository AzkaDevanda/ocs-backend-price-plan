package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACM_TIME_SPAN")
@SequenceGenerator(name = "ACM_TIME_SPAN_ID_SEQ", sequenceName = "ACM_TIME_SPAN_ID_SEQ", allocationSize = 1)
public class AcmTimeSpan implements Serializable {

    @Id
    @Column(name = "ACM_TIME_SPAN_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACM_TIME_SPAN_ID_SEQ")
    private Integer id;

    @Column(name = "PRICE_VER_ID", nullable = false)
    private Integer priceVerId;

    @Column(name = "ADJUST_METHOD", nullable = false)
    private Character adjustMethod;

    @Column(name = "TIME_SPAN_ID", nullable = false)
    private Integer timeSpanId;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "SP_ID")
    private Integer spId;

}