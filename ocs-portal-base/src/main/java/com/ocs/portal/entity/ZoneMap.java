package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "ZONE_MAP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneMap implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_map_id_seq")
    @SequenceGenerator(name = "zone_map_id_seq", sequenceName = "ZONE_MAP_ID_SEQ", allocationSize = 1)
    @Column(name = "ZONE_MAP_ID", nullable = false)
    private Integer id;

    @Column(name = "ZONE_MAP_NAME", nullable = false, length = 60)
    private String zoneMapName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "MATCH_MODE", nullable = false)
    private Character matchMode;

    @Column(name = "ZONE_MAP_TYPE")
    private Short zoneMapType;

    @Column(name = "STD_CODE", length = 30)
    private String stdCode;

    @Column(name = "GL_TYPE_ID")
    private Integer glTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "STATE")
    private Character state;
}
