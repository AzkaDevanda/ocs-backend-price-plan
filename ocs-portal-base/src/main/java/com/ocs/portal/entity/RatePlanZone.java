package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "RATE_PLAN_ZONE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatePlanZone implements Serializable {

    @Id
    @Column(name = "RATE_PLAN_ZONE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_seq_generator")
    @SequenceGenerator(name = "rate_seq_generator", sequenceName = "RATE_PLAN_ZONE_ID_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "RATE_PLAN_ID", nullable = false)
    private Integer ratePlanId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "ZONE_MAP_ID")
    private Integer zoneMapId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "MAPPING_SRC_TYPE")
    private Character mappingSrcType;

    @Column(name = "MAPPING_SRC_VALUE", length = 60)
    private String mappingSrcValue;

    @Column(name = "MAPPING_DES_TYPE")
    private Character mappingDesType;

    @Column(name = "MAPPING_DES_VALUE", length = 60)
    private String mappingDesValue;

    @Column(name = "LABEL_SHOW", length = 80)
    private String labelShow;

}