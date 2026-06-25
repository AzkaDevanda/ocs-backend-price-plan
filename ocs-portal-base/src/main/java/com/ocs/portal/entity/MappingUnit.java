package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MAPPING_UNIT")
public class MappingUnit implements Serializable {
    @EmbeddedId
    private MappingUnitId id;

    @Column(name = "ZONE_ID")
    private Integer zoneId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "MAPPING_MATCH_TYPE")
    private Character mappingMatchType;

    @Column(name = "MAPPING_TYPE")
    private Character mappingType;

    @Column(name = "MAPPING_VALUE", length = 60)
    private String mappingValue;
}