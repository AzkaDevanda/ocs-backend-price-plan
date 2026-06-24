package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ACM_THRESHOLD")
@AllArgsConstructor
@NoArgsConstructor
public class AcmThreshold implements Serializable {

    @Id
    @Column(name = "ACM_THRESHOLD_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACM_THRESHOLD_ID_SEQ_generator")
    @SequenceGenerator(name = "ACM_THRESHOLD_ID_SEQ_generator", sequenceName = "ACM_THRESHOLD_ID_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "TRIGGER_ID", nullable = false)
    private Integer triggerId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "INTERVAL")
    private Long interval;

    @Column(name = "RATIO")
    private Integer ratio;

    @Column(name = "ON_OFF_ATTR")
    private Integer onOffAttr;

}