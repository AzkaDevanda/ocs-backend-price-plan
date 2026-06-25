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
@Table(name = "ZONE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_id_seq")
    @SequenceGenerator(name = "zone_id_seq", sequenceName = "ZONE_ID_SEQ", allocationSize = 1)
    @Column(name = "ZONE_ID", nullable = false)
    private Long zoneId;

    @Column(name = "ZONE_MAP_ID", nullable = false)
    private Long zoneMapId;

    @Column(name = "PARENT_ZONE_ID")
    private Long parentZoneId;

    @Column(name = "ZONE_NAME", nullable = false, length = 120)
    private String zoneName;

    @Column(name = "COMMENTS", length = 500)
    private String comments;

    @Column(name = "ZONE_CODE", length = 250)
    private String zoneCode;

    @Column(name = "SP_ID")
    private Integer spId;
}
